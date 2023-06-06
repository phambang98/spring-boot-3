import {Component, OnInit} from '@angular/core';
import {filter, map} from "rxjs/operators";
import {NewChatComponent} from "../chat-list/new-chat/new-chat.component";
import {NewGroupComponent} from "../chat-list/new-group/new-group.component";
import {NbDialogService, NbMenuService} from "@nebular/theme";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../_services/user.service";
import {ChatService} from "../../_services/chat.service";
import {Observable} from "rxjs";
import {ChatModel} from "../../_dtos/chat/ChatModel";
import {UserProfile} from "../../_dtos/user/UserProfile";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit{


  chatModels: Observable<ChatModel[]>
  menu = [
    {title: 'Profile', icon: 'person-outline'},
    {title: 'New Chat', icon: 'person-add-outline'},
    {title: 'New Group', icon: 'plus-outline'},
    {title: 'Settings', icon: 'settings-outline'},
    {title: 'Log out', icon: 'unlock-outline'},
  ];

  badgeText: string = ' '
  profile: UserProfile

  constructor(private menuService: NbMenuService, private router: Router, private dialogService: NbDialogService,
              private userService: UserService, private chatService: ChatService, private route: ActivatedRoute) {
    this.profile = this.userService.getProfile()
    this.chatModels = this.chatService.getAllChat()
    this.chatModels['_value'].forEach(x => {
      this.chatService.updateStatusChat(x)
    })
    this.chatService.chatModel.subscribe((fp: ChatModel[]) => {
      this.chatModels['_value'] = fp
    })
  }

  ngOnInit(): void {

  }

  menuListener() {
    this.menuService.onItemClick()
      .pipe(
        filter(({tag}) => tag === 'context-chat-more'),
        map(({item: {title}}) => title),
      )
      .subscribe(title => {
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
          case 'Log out':
            this.userService.logout()
            this.router.navigateByUrl("/auth")
            break;
          default:
            break;
        }
      })
  }
}
