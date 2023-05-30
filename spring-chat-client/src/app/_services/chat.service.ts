import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {FriendProfile} from "../_dtos/chat/FriendProfile";
import {TokenStorageService} from "./token-storage.service";
import {WebSocketService} from "./web-socket.service";
import {MessageDetail} from "../_dtos/chat/MessageDetail";

@Injectable()
export class ChatService extends WebSocketService {

  private _fetch: BehaviorSubject<number> = new BehaviorSubject(0);
  public readonly fetch: Observable<number> = this._fetch.asObservable();

  public friendProfiles: Observable<FriendProfile[]>
  private myFriendProfiles: BehaviorSubject<FriendProfile[]> = new BehaviorSubject([])

  public friendProfileStatus: Observable<FriendProfile>
  private myFriendProfileStatus: BehaviorSubject<FriendProfile> = new BehaviorSubject(new FriendProfile())

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
    this.friendProfiles = this.myFriendProfiles.asObservable()
    this.friendProfileStatus = this.myFriendProfileStatus.asObservable()
  }

  updateFriends(newFriends: FriendProfile[], isUpdate: Boolean) {
    let friendValue = this.myFriendProfiles.value
    if (isUpdate && !friendValue.find(f => f.userId == newFriends[0].userId)) {
      newFriends.forEach(f => friendValue.push(f))
    }
    this.myFriendProfiles.next(friendValue)
  }

  onShowLastMsg(messageDetail: MessageDetail, recipientId ?: number) {
    if (!messageDetail) {
      let data = this.myFriendProfiles.value.find(x => x.userId == recipientId)
      data.lastTimeMsg = null
      data.lastMsg = null
    } else {
      let myFriend = this.myFriendProfiles.value.find(x => x.userId == messageDetail.senderId)
      if (!myFriend) {
        myFriend = this.myFriendProfiles.value.find(x => x.userId == messageDetail.recipientId)
      }
      let msg = ''
      if (messageDetail.senderId == this.tokenStorageService.getUser().id) {
        msg = 'Bạn :'
      }
      if (messageDetail.content) {
        myFriend.lastMsg = msg + messageDetail.content
      } else {
        myFriend.lastMsg = msg + messageDetail.files.length
      }
      myFriend.lastTimeMsg = new Date(messageDetail.createdAt)
    }
    this.sortFriends()
  }

  sortFriends() {
    this.myFriendProfiles.value.sort((obj1, obj2) => {
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


  fetchFriends(): Observable<FriendProfile[]> {
    return this.httpClient.get(`${environment.DOMAIN}/api/chat`, this.httpOptions)
      .pipe(map((friends: FriendProfile[]) => {
        this.updateFriends(friends, false)
        return friends
      }))
  }

  createFriend(userName: String): Observable<FriendProfile> {
    return this.httpClient.post(`${environment.DOMAIN}/api/chat?userName=${userName}`, this.httpOptions)
      .pipe(map((friend: FriendProfile) => {
        this.updateFriends([friend], true)
        return friend
      }))
  }

  getFriendsAll(): Observable<FriendProfile[]> {
    return this.myFriendProfiles
  }

  getFriend(id: number): FriendProfile {
    return this.myFriendProfiles.value.find(x => x.userId === id)
  }

  updateFetch(value) {
    this._fetch.next(value)
  }

  fetchFriend(value: FriendProfile[]) {
    this.myFriendProfiles.next(value)
  }

  onStatusReceived(status: any) {
    let json = JSON.parse(status.body)
    let data = json['data'] as FriendProfile
    if (json['type'] == "USER_CONVERSATION_UPDATED" || json['type'] == "USER_CONVERSATION_ADDED") {
      let data = json['data'] as FriendProfile
      this.updateFriends([data], true)
    }
    this.updateStatusFriend(data)
    this.myFriendProfileStatus.next(data)
  }

  updateStatusFriend(data: FriendProfile) {
    if (data.status === 'ONLINE') {
      data.status = 'success'
    } else if (data.status === 'OFFLINE') {
      data.status = 'danger'
    }
  }

  blockFriend(recipientId: number) {
    this.httpClient.post(`${environment.DOMAIN}/api/chat/${recipientId}`, this.httpOptions).subscribe((FriendProfile: number) => {

    })
  }

}
