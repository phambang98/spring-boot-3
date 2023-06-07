import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, HostListener,
  OnInit
} from '@angular/core';
import {ChatService} from 'src/app/_services/chat.service';
import {Router, ActivatedRoute} from '@angular/router';
import {ChatModel} from 'src/app/_dtos/chat/ChatModel';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {UserService} from 'src/app/_services/user.service';
import {MessageDetail} from 'src/app/_dtos/chat/MessageDetail';
import {MessageRequest} from "../../_dtos/chat/MessageRequest";
import {MessageService} from "../../_services/message.service";
import {ErrorService} from "../../_services/error.service";
import {environment} from "../../../environments/environment";
import {NbChatFormComponent, NbMenuService} from "@nebular/theme";
import {DomSanitizer} from "@angular/platform-browser";

declare var $: any;

@Component({
  selector: 'home-chat-detail',
  templateUrl: './chat-detail.component.html',
  styleUrls: ['./chat-detail.component.scss']
})
export class ChatDetailComponent implements OnInit {
  messages: MessageDetail[] = []
  chatModel: ChatModel
  myProfile: UserProfile
  subscription: any
  private messageId: number
  private chatId: number
  imgDropTypes = ['image/png', 'image/jpeg', 'image/gif']
  droppedFiles: any[] = []
  message: string = ''

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
              private menuService: NbMenuService, protected cd: ChangeDetectorRef, protected domSanitizer: DomSanitizer) {
    (async () => {
      this.route.params.subscribe(params => {
        if (this.subscription) this.subscription.unsubscribe()
        this.myProfile = this.userService.getProfile()
        this.chatModel = this.chatService.getOneChat(Number(params['chatId']))
        this.chatService.chatModel.subscribe((fps: ChatModel[]) => {
          this.chatModel = fps.find(x => x.chatId == this.chatModel.chatId)
        })
        this.showHideBlock()
        this.getChat()
      })
    })();
  }

  ngOnInit(): void {
    // this.menuChatDetail()
  }

  showHideBlock() {
    if (this.chatModel.chatType === environment.CHAT_GROUP) {
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
    this.menuService.onItemClick().subscribe((data) => {
      switch (data.item.title) {
        case 'Delete':
          this.messageService.deleteMessage(this.chatId, this.messageId)
          break;
        case 'Update':
          break;
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
        case 'Reply':
          break;
        case 'Profile Friend':
          this.router.navigate(['/profile-friend'], {
            queryParams: {
              friendId: this.chatModel.userId,
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
    this.subscription = this.messageService.fetchMessages(this.chatModel.chatId).subscribe({
      next: (arrayMsg: MessageDetail[]) => {

        this.messages.push(...arrayMsg.slice(this.messages.length, arrayMsg.length))
        this.messageService.nbMessages.subscribe((mapMessage: Map<number, MessageDetail[]>) => {
          this.messages = mapMessage.get(this.chatModel.chatId)
        })
      }, error: (e) => {
        this.errorService.errorFetch(e)
      }
    })
  }

  onSendMessage() {
    if (!this.message) {
      return
    }
    const files = !this.droppedFiles.length ? [] : this.droppedFiles;
    let messageRequest = new MessageRequest(this.chatModel.userId, this.message, null, this.chatModel.chatType, this.chatModel.chatId);
    this.messageService.createMessage(messageRequest, files)
    if (this.droppedFiles.length) {
      this.droppedFiles = []
    }
    this.message = ''
    this.cd.detectChanges()
  }

  friendActionMenuBtn() {
    $('.friend-action-menu').toggle()
  }


  @HostListener('drop', ['$event'])
  onDrop(event: any) {
    event.preventDefault()
    event.stopPropagation()
    if (event.dataTransfer && event.dataTransfer.files) {
      for (const file of event.dataTransfer.files) {
        const res = file
        if (this.imgDropTypes.includes(file.type)) {
          const fr = new FileReader();
          fr.onload = (e: any) => {
            res.src = e.target.result
            res.urlStyle = this.domSanitizer.bypassSecurityTrustStyle(`url(${res.src})`)
            this.cd.detectChanges()
          }
          fr.readAsDataURL(file)
        }
        this.droppedFiles.push(res)
      }
    }
  }

  removeFile(file) {
    const index = this.droppedFiles.indexOf(file)
    if (index >= 0) {
      this.droppedFiles.splice(index, 1)
    }
  }

  @HostListener('dragover', ['$event'])
  @HostListener('dragleave', ['$event'])
  onWindowDragEnter(event: any): void {
    event.preventDefault()
    event.stopPropagation()
  }
}
