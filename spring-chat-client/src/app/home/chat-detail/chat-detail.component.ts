import {
  AfterContentInit,
  AfterViewInit,
  Component,
  OnInit
} from '@angular/core';
import {ChatService} from 'src/app/_services/chat.service';
import {Router, ActivatedRoute} from '@angular/router';
import {FriendProfile} from 'src/app/_dtos/chat/FriendProfile';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {UserService} from 'src/app/_services/user.service';
import {MessageDetail} from 'src/app/_dtos/chat/MessageDetail';
import {MessageRequest} from "../../_dtos/chat/MessageRequest";
import {MessageService} from "../../_services/message.service";
import {ErrorService} from "../../_services/error.service";
import {NbMenuItem, NbMenuService} from "@nebular/theme";
import {filter, map} from "rxjs/operators";
import {NewChatComponent} from "../chat-list/new-chat/new-chat.component";

@Component({
  selector: 'app-chat-detail',
  templateUrl: './chat-detail.component.html',
  styleUrls: ['./chat-detail.component.scss']
})
export class ChatDetailComponent implements OnInit {

  messages: MessageDetail[] = []
  friendId: number
  friendProfile: FriendProfile
  myProfile: UserProfile
  subscription: any

  private messageId: number
  menuFriend = [
    {title: 'Profile Friend', icon: 'person-outline'},
    {title: 'Block', icon: 'close-circle-outline'},
    {title: 'Un Block', icon: 'checkmark-circle-outline'},
  ]

  eventMessageMain = [
    {title: 'Update', icon: 'edit-outline'},
    {title: 'Delete', icon: 'trash-2-outline'},
  ]

  eventMessageSub = [
    {title: 'Reply', icon: 'corner-up-left-outline'},
  ]

  constructor(private chatService: ChatService, private messageService: MessageService, private router: Router,
              private route: ActivatedRoute, private userService: UserService, private errorService: ErrorService,
              private menuService: NbMenuService) {

    (async () => {
      this.route.params.subscribe(params => {
        this.friendId = params['id']
        if (this.subscription) this.subscription.unsubscribe()
        this.myProfile = this.userService.getProfile()
        this.friendProfile = this.chatService.getFriend(Number(this.friendId))
        this.getChat()
      })
    })();
  }

  ngOnInit(): void {
    this.menuChatDetail()
  }

  clickId(messageId) {
    this.messageId = messageId
  }

  menuChatDetail() {
    this.menuService.onItemClick().subscribe((data) => {
      switch (data.item.title) {
        case 'Delete':
          this.messageService.deleteMessage(this.messageId, this.friendId)
          break;
        case 'Update':
          break;
        case 'Block':
          this.chatService.blockFriend(this.friendId)
          break;
        case 'Un Block':
          break;
        case 'Reply':
          break;
        case 'Profile Friend':
          this.router.navigate(['/profile-friend'], {queryParams: {friendId: this.friendId, isFriend: true,}});
          break;
        default:
          break;
      }
    });
  }

  getChat() {
    this.messages = []

    this.subscription = this.messageService.fetchMessages(this.friendId).subscribe({
      next: (arrayMsg: MessageDetail[]) => {
        arrayMsg.forEach(msg => {
          if (msg.senderId == this.myProfile.id) {
            msg.imageUrl = this.myProfile.imageUrl
            msg.reply = true
          } else {
            msg.imageUrl = this.chatService.getFriend(msg.senderId).imageUrl
            msg.reply = false
          }
        })
        this.messages.push(...arrayMsg.slice(this.messages.length, arrayMsg.length))
        this.messageService.nbMessages.subscribe((message: MessageDetail[]) => {
          this.messages = message
        })
      }, error: (e) => {
        this.errorService.errorFetch(e)
      }
    })
  }

  sendMessage(event) {
    const files = !event.files ? [] : event.files;
    let messageRequest = new MessageRequest(this.friendId, event.message, null);
    this.messageService.createMessage(messageRequest, files)
  }

}
