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

  nbMessages: Observable<MessageDetail[]>
  private myNbMessages: BehaviorSubject<MessageDetail[]> = new BehaviorSubject([])

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  myProfile: UserProfile;

  constructor(private httpClient: HttpClient, private chatService: ChatService, private userService: UserService,
              protected tokenStorageService: TokenStorageService, protected notificationService: NotificationService) {
    super(tokenStorageService)
    this.httpOptions.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.tokenStorageService.getToken()
    })
    this.nbMessages = this.myNbMessages.asObservable()
    this.myProfile = this.userService.getProfile()
  }


  onMessageReceived(message) {
    let json = JSON.parse(message.body)
    if (json['type'] == "USER_MESSAGE_ADDED") {
      let data = json['data'] as MessageDetail
      this.onUpdateMessage([data], true)
    } else if (json['type'] == "USER_MESSAGE_DELETE") {
      let data = json['data'] as DeleteMessageDetail
      this.onDeleteMessage(data.messageId, data.recipientId)
    } else {
      console.log("json", json)
    }
  }


  onSendMessage(messageRequest: MessageRequest) {
    this.stompClient.send("/app/message", {}, JSON.stringify(messageRequest));
  }

  onUpdateMessage(messageDetails: MessageDetail[], isReceived: Boolean) {
    let messageDetailValue: MessageDetail[] = []
    if (isReceived) {
      messageDetailValue = this.myNbMessages.value
    }
    messageDetails.forEach(m => {
      if (m.senderId == this.myProfile.id) {
        m.imageUrl = this.myProfile.imageUrl
        m.reply = true
      } else {
        m.imageUrl = this.chatService.getFriend(m.senderId).imageUrl
        m.reply = false
        if (isReceived) {
          this.notificationService.newMessage("You have new message :" + this.chatService.getFriend(m.senderId).userName, m.content)
        }
      }
    })
    this.myNbMessages.next(messageDetailValue.concat(messageDetails));
    if (isReceived) {
      this.chatService.onShowLastMsg(messageDetails[0])
    }
  }

  fetchMessages(recipientId: number): Observable<MessageDetail[]> {
    return this.httpClient.get(`${environment.DOMAIN}/api/message/${recipientId}`, this.httpOptions)
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
      this.createMessageFile(formData).subscribe({
          next: (v: MessageDetail) => {
            this.onSendMessage(new MessageRequest(messageRequest.recipientId, messageRequest.content, v.messageId))
          },
          error: (err) => {
            console.log("err-createMessageFile", err)
          }
        }
      )
    } else {
      this.onSendMessage(new MessageRequest(messageRequest.recipientId, messageRequest.content, null))
    }
  }

  createMessageFile(data: FormData): Observable<MessageDetail> {
    return this.httpClient
      .post(`${environment.DOMAIN}/api/message/files`, data)
      .pipe(map((v: MessageDetail) => {
        return v
      }))
  }

  deleteMessage(messageId: number, recipientId: number) {
    this.httpClient.delete(`${environment.DOMAIN}/api/message/${messageId}`, this.httpOptions).subscribe((messageIdSuccess: number) => {
      this.onDeleteMessage(messageIdSuccess, recipientId)
    })
  }

  onDeleteMessage(messageId: number, recipientId: number) {
    this.myNbMessages.next(this.myNbMessages.value.filter(obj => obj.messageId !== messageId));
    this.chatService.onShowLastMsg(this.myNbMessages.value[this.myNbMessages.value.length - 1], recipientId)
  }
}
