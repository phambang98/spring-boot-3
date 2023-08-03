import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {MessageDetail} from "../_dtos/chat/MessageDetail";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ChatService} from "./chat.service";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {MessageRequest} from "../_dtos/chat/MessageRequest";
import {UserService} from "./user.service";
import {UserProfile} from "../_dtos/user/UserProfile";
import {WebSocketService} from "./web-socket.service";
import {TokenStorageService} from "./token-storage.service";
import {NotificationService} from "./notification.service";
import {DeleteMessageDetail} from "../_dtos/chat/DeleteMessageDetail";

@Injectable()
export class MessageService extends WebSocketService {

  nbMessages: Observable<Map<number, MessageDetail[]>>
  private myNbMessages: BehaviorSubject<Map<number, MessageDetail[]>> = new BehaviorSubject(new Map())

  myProfile: UserProfile;

  constructor(private httpClient: HttpClient, private chatService: ChatService, private userService: UserService,
              protected tokenStorageService: TokenStorageService, protected notificationService: NotificationService) {
    super(tokenStorageService)
    this.nbMessages = this.myNbMessages.asObservable()
    this.myProfile = this.userService.getProfile()
  }


  onMessageReceived(message) {
    let json = JSON.parse(message.body)
    if (json['type'] == "USER_MESSAGE_ADDED" || json['type'] == "USER_MESSAGE_GROUP_ADDED") {
      let data = json['data'] as MessageDetail
      this.onUpdateMessage([data], true)
    } else if (json['type'] == "USER_MESSAGE_DELETE") {
      let data = json['data'] as DeleteMessageDetail
      this.onDeleteMessage(data.chatId, data.messageId)
    } else {
      console.log("json", json)
    }
  }

  onSendMessage(messageRequest: MessageRequest) {
    this.stompClient.send("/app/message", {}, JSON.stringify(messageRequest));
  }

  onUpdateMessage(messageDetails: MessageDetail[], isReceived: Boolean) {
    messageDetails.forEach(x => {
      if (x.senderId == this.myProfile.id) {
        x.reply = true
      } else {
        x.reply = false
      }
    })
    if (isReceived) {
      if (messageDetails[0].senderId !== this.myProfile.id) {
        this.notificationService.newMessage("You have new message :" + this.chatService.getOneChat(messageDetails[0].chatId).userName, messageDetails[0].content)
      }
      let messages = this.myNbMessages.value.get(messageDetails[0].chatId)
      if (messages) {
        this.myNbMessages.value.set(messageDetails[0].chatId, messages.concat(messageDetails))
      }
    } else {
      if (messageDetails && messageDetails.length != 0) {
        this.myNbMessages.value.set(messageDetails[0].chatId, messageDetails)
      }
    }
    this.myNbMessages.next(this.myNbMessages.value);
    if (isReceived) {
      this.chatService.onShowLastMsg(messageDetails[0])
    }
  }

  fetchMessages(chatId: number): Observable<MessageDetail[]> {
    return this.httpClient.get(`${environment.DOMAIN}/api/message/${chatId}`)
      .pipe(map((msg: MessageDetail[]) => {
        this.onUpdateMessage(msg, false)
        return msg
      }))
  }

  createMessage(messageRequest: MessageRequest, files: any) {
    if (files.length != 0) {
      let formData = new FormData();
      files.forEach(file => {
        formData.append('files', file)
      })
      formData.append('recipientId', messageRequest.recipientId + "")
      formData.append('chatType', messageRequest.chatType)
      this.createMessageFile(formData).subscribe({
          next: (v: MessageDetail) => {
            this.onSendMessage(new MessageRequest(messageRequest.recipientId, messageRequest.content, v.messageId, messageRequest.chatType, messageRequest.chatId))
          },
          error: (err) => {
            console.log("err-createMessageFile", err)
          }
        }
      )
    } else {
      this.onSendMessage(new MessageRequest(messageRequest.recipientId, messageRequest.content, null, messageRequest.chatType, messageRequest.chatId))
    }
  }

  createMessageFile(data: FormData): Observable<MessageDetail> {
    return this.httpClient
      .post(`${environment.DOMAIN}/api/message/files`, data)
      .pipe(map((v: MessageDetail) => {
        return v
      }))
  }

  deleteMessage(chatId: number, messageId: number) {
    this.httpClient.delete(`${environment.DOMAIN}/api/message/${messageId}`).subscribe({
      complete: () => {
        this.onDeleteMessage(chatId, messageId)
      },
      error: (e) => {
        console.log(e)
      }
    })
  }

  onDeleteMessage(chatId: number, messageId: number) {
    this.myNbMessages.value.set(chatId, this.myNbMessages.value.get(chatId).filter(obj => obj.messageId !== messageId))
    this.myNbMessages.next(this.myNbMessages.value);
    this.chatService.onShowLastMsg(this.myNbMessages.value[this.myNbMessages.value.get(chatId).length - 1], chatId)
  }
}
