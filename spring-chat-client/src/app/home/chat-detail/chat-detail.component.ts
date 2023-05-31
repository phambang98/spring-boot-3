import {
  AfterViewInit,
  Component, ElementRef,
  OnInit, Renderer2
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
import {NbMenuService} from "@nebular/theme";

@Component({
  selector: 'app-chat-detail',
  templateUrl: './chat-detail.component.html',
  styleUrls: ['./chat-detail.component.scss']
})
export class ChatDetailComponent implements OnInit {

  messages: MessageDetail[] = []
  friendProfile: FriendProfile
  myProfile: UserProfile
  subscription: any
  private messageId: number
  menuFriend = [
    {id: 1, title: 'Profile Friend', icon: 'person-outline'},
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
              private menuService: NbMenuService, private renderer: Renderer2, private elementRef: ElementRef) {

    (async () => {
      this.route.params.subscribe(params => {
        if (this.subscription) this.subscription.unsubscribe()
        this.myProfile = this.userService.getProfile()
        this.friendProfile = this.chatService.getFriend(Number(params['id']))
        this.chatService.friendProfiles.subscribe((fps: FriendProfile[]) => {
          this.friendProfile = fps.find(x => x.userId = this.friendProfile.userId)
        })
        this.showHideBlock()
        this.getChat()
      })
    })();
  }

  ngOnInit(): void {
    this.menuChatDetail()
  }

  showHideBlock() {
    if (this.friendProfile.blockedBy) {
      this.menuFriend = this.menuFriend.filter(x => x.id === 1)
      if (this.friendProfile.blockedBy === this.myProfile.id) {
        this.menuFriend.push({id: 2, title: 'Un Block', icon: 'checkmark-circle-outline'})
      } else {
        this.menuFriend.push({id: 2, title: 'You are blocked', icon: 'close-circle-outline'})
      }

    } else {
      this.menuFriend = this.menuFriend.filter(x => x.id === 1)
      this.menuFriend.push({id: 3, title: 'Block', icon: 'close-circle-outline'})
    }
  }

  clickId(messageId) {
    this.messageId = messageId
  }

  menuChatDetail() {
    this.menuService.onItemClick().subscribe((data) => {
      switch (data.item.title) {
        case 'Delete':
          this.messageService.deleteMessage(this.messageId, this.friendProfile.userId)
          break;
        case 'Update':
          break;
        case 'Block':
          this.chatService.blockFriend(this.friendProfile.userId).subscribe({
            next: (value: FriendProfile) => {
              this.friendProfile = value
              this.showHideBlock()
            },
            error: (err) => {
              console.log("err-block-friend", err)
            }
          })
          break;
        case 'Un Block':
          this.chatService.unblockFriend(this.friendProfile.userId).subscribe({
            next: (value: FriendProfile) => {
              this.friendProfile = value
              this.showHideBlock()
            },
            error: (err) => {
              console.log("err-block-friend", err)
            }
          })
          break;
        case 'Reply':
          break;
        case 'Profile Friend':
          this.router.navigate(['/profile-friend'], {
            queryParams: {
              friendId: this.friendProfile.userId,
              isFriend: true,
            }
          });
          break;
        default:
          break;
      }
    });
  }

  getChat() {
    this.messages = []
    this.subscription = this.messageService.fetchMessages(this.friendProfile.userId).subscribe({
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
    let messageRequest = new MessageRequest(this.friendProfile.userId, event.message, null);
    this.messageService.createMessage(messageRequest, files)
  }

}
