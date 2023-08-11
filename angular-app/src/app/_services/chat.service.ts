import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {ChatModel} from "../_dtos/chat/ChatModel";
import {TokenStorageService} from "./token-storage.service";
import {WebSocketService} from "./web-socket.service";
import {MessageDetail} from "../_dtos/chat/MessageDetail";
import {ChatGroupModel} from "../_dtos/chat/ChatGroupModel";
import {UserChatGroupModel} from "../_dtos/chat/UserChatGroupModel";

@Injectable()
export class ChatService extends WebSocketService {


  public chatModel: Observable<ChatModel[]>
  private myChatModel: BehaviorSubject<ChatModel[]> = new BehaviorSubject([])

  constructor(private httpClient: HttpClient, protected tokenStorageService: TokenStorageService) {
    super(tokenStorageService)
    this.chatModel = this.myChatModel.asObservable()
  }

  updateFetchChats(newChats: ChatModel[], isUpdate: Boolean) {
    let chatsValue = this.myChatModel.value
    if (isUpdate) {
      let data = chatsValue.find(f => f.chatId == newChats[0].chatId)
      if (!data) {
        newChats.forEach(f => chatsValue.push(f))
      } else {
        data.status = newChats[0].status
        data.lastTimeLogin = newChats[0].lastTimeLogin
        data.blockedBy = newChats[0].blockedBy
      }
    }
    this.myChatModel.next(chatsValue)
  }

  onShowLastMsg(messageDetail: MessageDetail, chatId ?: number) {
    if (!messageDetail) {
      let data = this.myChatModel.value.find(x => x.chatId == chatId)
      data.lastTimeMsg = null
      data.lastMsg = null
    } else {
      let myChat = this.myChatModel.value.find(x => x.chatId == messageDetail.chatId)
      let msg = ''
      if (messageDetail.senderId == this.tokenStorageService.getUser().id) {
        msg = 'Bạn :'
      }
      if (messageDetail.content) {
        myChat.lastMsg = msg + messageDetail.content
      } else {
        myChat.lastMsg = "send " + msg + messageDetail.files.length + " file"
      }
      myChat.lastTimeMsg = new Date(messageDetail.createdAt)
    }
    this.sortChats()
  }

  sortChats() {
    this.myChatModel.value.sort((obj1, obj2) => {
      let o1 = new Date(obj1.lastTimeMsg);
      let o2 = new Date(obj2.lastTimeMsg);
      if (o2 > o1) {
        return 1
      }
      if (o2 < o1) {
        return -1
      }
      return 0
    })
  }


  fetchChats(): Observable<ChatModel[]> {
    return this.httpClient.get(`${environment.DOMAIN}/api/chat`)
      .pipe(map((chats: ChatModel[]) => {
        this.updateFetchChats(chats, false)
        return chats
      }))
  }

  createChat(userName: String): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat?userName=${userName}`, null)
      .pipe(map((chats: ChatModel) => {
        // this.updateFetchChats([chats], true)
        return chats
      }))
  }

  createChatGroup(chatGroupModel: ChatGroupModel): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat-group/create`, JSON.stringify(chatGroupModel))
      .pipe(map((chats: ChatModel) => {
        return chats
      }))
  }

  addUserChatGroup(userChatGroupModel: UserChatGroupModel): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat-group/add-user`, JSON.stringify(userChatGroupModel))
      .pipe(map((chats: ChatModel) => {
        return chats
      }))
  }

  removeUserChatGroup(chatGroupModel: ChatGroupModel): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat-group/remove-user`, JSON.stringify(chatGroupModel))
      .pipe(map((chats: ChatModel) => {
        return chats
      }))
  }

  leaveChatGroup(chatGroupModel: ChatGroupModel): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat-group/leave`, JSON.stringify(chatGroupModel))
      .pipe(map((chats: ChatModel) => {
        return chats
      }))
  }


  getAllChat(): Observable<ChatModel[]> {
    return this.myChatModel
  }

  getOneChat(chatId: number): ChatModel {
    return this.myChatModel.value.find(x => x.chatId === chatId)
  }

  fetchChat(value: ChatModel[]) {
    this.myChatModel.next(value)
  }

  onStatusReceived(status: any) {
    let json = JSON.parse(status.body)
    let data = json['data'] as ChatModel
    if (json['type'] == "USER_CONVERSATION_UPDATED" || json['type'] == "USER_CONVERSATION_ADDED" || json['type'] == "USER_STATUS"
      || json['type'] == "USER_CONVERSATION_BLOCK" || json['type'] == "USER_CONVERSATION_UNBLOCK") {
      this.updateStatusChat(data)
      this.updateFetchChats([data], true)
    }
  }

  updateStatusChat(data: ChatModel) {
    if (data.status === 'ONLINE') {
      data.status = 'success'
    } else if (data.status === 'OFFLINE') {
      if (data.blockedBy) {
        data.status = ''
      } else {
        data.status = 'danger'
      }
    }
  }

  blockChat(chatId: number): Observable<any> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat/block/${chatId}`, null)
      .pipe(map((chat: ChatModel) => {
        this.updateStatusChat(chat)
        this.updateFetchChats([chat], true)
        return chat
      }))
  }

  unblockChat(chatId: number): Observable<any> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat/unblock/${chatId}`, null)
      .pipe(map((chat: ChatModel) => {
        this.updateStatusChat(chat)
        this.updateFetchChats([chat], true)
        return chat
      }))
  }

}
