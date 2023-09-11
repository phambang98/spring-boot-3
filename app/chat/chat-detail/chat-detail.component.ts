import {
  Component, HostListener,
  OnInit
} from '@angular/core';
import {ChatService} from 'src/app/_services/chat.service';
import {Router, ActivatedRoute} from '@angular/router';
import {ChatModel} from 'src/app/_dtos/chat/ChatModel';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {UserService} from 'src/app/_services/user.service';
import {MessageDetail} from 'src/app/_dtos/chat/MessageDetail';
import {NbDialogService, NbMenuService} from "@nebular/theme";
import {MessageService} from "../../_services/message.service";
import {ErrorService} from "../../_services/error.service";
import {environment} from "../../../environments/environment";
import {MessageRequest} from "../../_dtos/chat/MessageRequest";
import {UserChatGroupModel} from "../../_dtos/chat/UserChatGroupModel";
import {AddUserGroupComponent} from "../add-user-group/add-user-group.component";
import {CloseDialog} from "../../_dtos/chat/CloseDialog";
import {filter, map} from "rxjs/operators";

@Component({
  selector: 'app-chat-detail',
  templateUrl: './chat-detail.component.html',
  styleUrls: ['./chat-detail.component.scss']
})
export class ChatDetailComponent implements OnInit {

  messages: MessageDetail[] = []
  chatModel: ChatModel
  myProfile: UserProfile
  messageId: number
  chatId: number
  menuFriend = [
    {id: 1, title: 'Profile Friend', icon: 'person-outline'},
  ]
  subscription: any

  eventMessageMain = [
    {title: 'Update', icon: 'edit-outline'},
    {title: 'Delete', icon: 'trash-2-outline'},
  ]

  eventMessageSub = [
    {title: 'Reply', icon: 'corner-up-left-outline'},
  ]

  constructor(private chatService: ChatService, private messageService: MessageService, private router: Router,
              private route: ActivatedRoute, private userService: UserService, private errorService: ErrorService,
              private menuService: NbMenuService, private dialogService: NbDialogService) {
    (async () => {
      this.route.params.subscribe(params => {
        if (params['chatId']) {
          let chatId = Number(params['chatId'])
          if (this.subscription) this.subscription.unsubscribe()
          this.messages = []
          this.myProfile = this.userService.getProfile()
          this.chatModel = this.chatService.getOneChat(chatId)

          this.messageService.fetchMessages(chatId).subscribe({
            next: (arrayMsg: MessageDetail[]) => {
              this.messages.push(...arrayMsg?.slice(this.messages.length, arrayMsg.length))
              this.messageService.nbMessages.subscribe((mapMessage: Map<number, MessageDetail[]>) => {
                this.messages = mapMessage.get(this.chatModel.chatId)
              })
            }, error: (e) => {
              console.log("error", e)
            }
          })
          this.showHideBlock()
          this.getChat()
          this.menuChatDetail()
          this.menuMessageDetail()
        }
      })
    })();
  }

  ngOnInit(): void {
  }


  getChat() {
    this.messages = []
    this.subscription = this.messageService.fetchMessages(this.chatModel.chatId).subscribe({
      next: (arrayMsg: MessageDetail[]) => {

        this.messages.push(...arrayMsg?.slice(this.messages.length, arrayMsg.length))
        this.messageService.nbMessages.subscribe((mapMessage: Map<number, MessageDetail[]>) => {
          this.messages = mapMessage.get(this.chatModel.chatId)
        })
      }, error: (e) => {
        console.log("error", e)
      }
    })
  }

  showHideBlock() {
    if (this.chatModel.chatType === 'GROUP') {
      this.menuFriend.push({id: 4, title: 'Add User From Group', icon: 'person-add-outline'})
      return
    }
    if (this.chatModel.blockedBy) {
      this.menuFriend = this.menuFriend.filter(x => x.id === 1)
      if (this.chatModel.blockedBy === this.myProfile.id) {
        this.menuFriend.push({id: 2, title: 'Un Block', icon: 'checkmark-circle-outline'})
      } else {
        this.menuFriend.push({id: 2, title: 'You are blocked', icon: 'close-circle-outline'})
      }
    } else {
      this.menuFriend = this.menuFriend.filter(x => x.id === 1)
      this.menuFriend.push({id: 3, title: 'Block', icon: 'close-circle-outline'})
    }
  }

  clickId(chatId, messageId) {
    this.chatId = chatId
    this.messageId = messageId
  }

  menuChatDetail() {
    this.menuService.onItemClick()
      .pipe(
        filter(({tag}) => tag === 'chat-detail-more'),
        map(({item: {title}}) => title),
      )
      .subscribe((title) => {
        switch (title) {
          case 'Block':
            this.chatService.blockChat(this.chatModel.chatId).subscribe({
              next: (value: ChatModel) => {
                this.chatModel = value
                this.showHideBlock()
              },
              error: (err) => {
                console.log("err-block-friend", err)
              }
            })
            break;
          case 'Un Block':
            this.chatService.unblockChat(this.chatModel.chatId).subscribe({
              next: (value: ChatModel) => {
                this.chatModel = value
                this.showHideBlock()
              },
              error: (err) => {
                console.log("err-block-friend", err)
              }
            })
            break;
          case 'Profile Friend':
            this.router.navigate(['/chat/profile-friend'], {
              queryParams: {
                friendId: this.chatModel.userId,
                isFriend: true,
              }
            });
            break;
          case 'Add User From Group' :
            this.dialogService.open(AddUserGroupComponent, {
              context: this.chatModel.userName
            }).onClose.subscribe((closeDialog: CloseDialog) => {
              if (closeDialog?.submit) {
                this.chatService.addUserChatGroup(new UserChatGroupModel(closeDialog.data, this.chatId)).subscribe({
                  complete: () => {
                    console.log("add-user-chat-group-complete");
                  },
                  error: (err) => {
                    console.log("err-createFriend", err);
                  },
                })
              }

            })
            break
          default:
            break;
        }
      });
  }

  menuMessageDetail() {
    this.menuService.onItemClick()
      .pipe(
        filter(({tag}) => tag === 'message-detail-more'),
        map(({item: {title}}) => title),
      )
      .subscribe((title) => {
        switch (title) {
          case 'Delete':
            this.messageService.deleteMessage(this.chatId, this.messageId)
            break;
          case 'Update':
            break;
          case 'Reply':
            break;
          default:
            break;
        }
      });
  }

  sendMessage(event) {
    const files = !event.files ? [] : event.files;
    let messageRequest = new MessageRequest(this.chatModel.userId, event.message, null, this.chatModel.chatType, this.chatModel.chatId);
    this.messageService.createMessage(messageRequest, files)
  }

  @HostListener('keydown', ['$event'])
  @HostListener('paste', ['$event'])
  @HostListener('cut', ['$event'])
  onEvent(event) {
    if (this.chatModel.blockedBy) {
      event.preventDefault()
    }
  }
}
