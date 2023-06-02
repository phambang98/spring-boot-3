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

@Injectable()
export class ChatService extends WebSocketService {

  private fetch: BehaviorSubject<number> = new BehaviorSubject(0);
  public readonly myFetch: Observable<number> = this.fetch.asObservable();

  public chatModel: Observable<ChatModel[]>
  private myChatModel: BehaviorSubject<ChatModel[]> = new BehaviorSubject([])

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private httpClient: HttpClient, protected tokenStorageService: TokenStorageService) {
    super(tokenStorageService)
    this.httpOptions.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.tokenStorageService.getToken()
    })
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
        myChat.lastMsg = msg + messageDetail.files.length
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
    return this.httpClient.get(`${environment.DOMAIN}/api/chat`, this.httpOptions)
      .pipe(map((chats: ChatModel[]) => {
        this.updateFetchChats(chats, false)
        return chats
      }))
  }

  createChat(userName: String): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat?userName=${userName}`, this.httpOptions)
      .pipe(map((chats: ChatModel) => {
        this.updateFetchChats([chats], true)
        return chats
      }))
  }

  createChatGroup(chatGroupModel: ChatGroupModel): Observable<ChatModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat/group-new`, JSON.stringify(chatGroupModel),this.httpOptions)
      .pipe(map((chats: ChatModel) => {
      this.updateFetchChats([chats], true)
      return chats
    }))
  }


  getAllChat(): Observable<ChatModel[]> {
    return this.myChatModel
  }

  getOneChat(chatId: number): ChatModel {
    return this.myChatModel.value.find(x => x.chatId === chatId)
  }

  updateFetch(value) {
    this.fetch.next(value)
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
    return this.httpClient.post(`${environment.DOMAIN}/api/chat/block/${chatId}`, this.httpOptions)
      .pipe(map((chat: ChatModel) => {
        this.updateStatusChat(chat)
        this.updateFetchChats([chat], true)
        return chat
      }))
  }

  unblockChat(chatId: number): Observable<any> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat/unblock/${chatId}`, this.httpOptions)
      .pipe(map((chat: ChatModel) => {
        this.updateStatusChat(chat)
        this.updateFetchChats([chat], true)
        return chat
      }))
  }

}
