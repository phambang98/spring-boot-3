import {AfterViewInit, Component, OnInit} from '@angular/core';
import {NbMenuService, NbDialogService} from '@nebular/theme';
import {filter, map} from 'rxjs/operators';
import {Router, ActivatedRoute} from '@angular/router';
import {UserService} from 'src/app/_services/user.service';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {Observable} from 'rxjs';
import {ChatModel} from 'src/app/_dtos/chat/ChatModel';
import {NewChatComponent} from '../new-chat/new-chat.component';
import {ChatService} from "../../_services/chat.service";
import {NewGroupComponent} from "../new-group/new-group.component";
import {ChatGroupModel} from "../../_dtos/chat/ChatGroupModel";

@Component({
  selector: 'home-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.scss']

})
export class ChatListComponent implements OnInit, AfterViewInit {

  chatModels: Observable<ChatModel[]>
  menu = [
    {title: 'Profile', icon: 'person-outline'},
    {title: 'New Chat', icon: 'person-add-outline'},
    {title: 'New Group', icon: 'plus-outline'},
    {title: 'Settings', icon: 'settings-outline'},
    {title: 'Log out', icon: 'unlock-outline'},
    {title: 'Lucky-wheel', icon: 'settings-outline'},
  ];

  badgeText: string = ' '
  profile: UserProfile

  constructor(private menuService: NbMenuService, private router: Router, private dialogService: NbDialogService,
              private userService: UserService, private chatService: ChatService, private route: ActivatedRoute) {
    this.profile = this.userService.getProfile()
    this.chatModels = this.chatService.getAllChat()
  }

  ngAfterViewInit() {
    this.chatModels['_value'].forEach(x => {
      this.chatService.updateStatusChat(x)
    })
    this.chatService.chatModel.subscribe((fp: ChatModel[]) => {
      this.chatModels['_value'] = fp
    })
  }

  ngOnInit(): void {
    this.menuListener()
  }

  menuListener() {
    this.menuService.onItemClick()
      .pipe(
        filter(({tag}) => tag === 'context-chat-more'),
        map(({item: {title}}) => title),
      )
      .subscribe((title: string) => {
        switch (title) {
          case 'Profile':
            this.router.navigateByUrl("/profile")
            break;
          case 'New Chat':
            this.dialogService.open(NewChatComponent).onClose.subscribe((userName) => {
              this.chatService.createChat(userName).subscribe({
                complete: () => {
                  console.log("complete-createFriend");
                },
                next: (v) => {
                  let newChat = this.chatModels['_value'].find(x => x.userId === v.userId)
                  newChat.lastTimeLogin = v.lastTimeLogin
                  newChat.status = v.status
                  this.chatService.updateStatusChat(newChat)
                },
                error: (err) => {
                  console.log("err-createFriend", err);
                },
              })
            })
            break;
          case 'New Group':
            this.dialogService.open(NewGroupComponent).onClose.subscribe((chatGroupModel) => {
              this.chatService.createChatGroup(chatGroupModel).subscribe({
                complete: () => {
                  console.log("complete-group-chat");
                },
                next: (v) => {
                  let newChat = this.chatModels['_value'].find(x => x.userId === v.userId)
                  newChat.lastTimeLogin = v.lastTimeLogin
                  newChat.status = v.status
                  this.chatService.updateStatusChat(newChat)
                },
                error: (err) => {
                  console.log("err-createFriend", err);
                },
              })
            })
            break;
          case 'Settings':
            this.router.navigateByUrl("/settings")
            break;
          case 'Lucky-wheel':
            this.router.navigateByUrl("/lucky-wheel")
            break;
          case 'Log out':
            this.userService.logout()
            this.router.navigateByUrl("/auth")
            break;
          default:
            break;
        }
      })
  }

  chatClicked(chatId: Number) {
    this.router.navigate([chatId], {relativeTo: this.route, skipLocationChange: true})
  }
}
