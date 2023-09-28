import {Component, OnInit} from '@angular/core';
import {ChatModel} from "../_dtos/chat/ChatModel";
import {UserProfile} from "../_dtos/user/UserProfile";
import {NbDialogService, NbMenuService} from "@nebular/theme";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../_services/user.service";
import {ChatService} from "../_services/chat.service";
import {filter, map} from "rxjs/operators";
import {NewChatComponent} from "./new-chat/new-chat.component";
import {CloseDialog} from "../_dtos/chat/CloseDialog";
import {NewGroupComponent} from "./new-group/new-group.component";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  chatModel: ChatModel[]
  chatListMenu = [
    {title: 'Profile', icon: 'person-outline'},
    {title: 'New Chat', icon: 'person-add-outline'},
    {title: 'New Group', icon: 'plus-outline'},
    {title: 'Log out', icon: 'unlock-outline'},
  ];

  badgeText: string = ' '
  profile: UserProfile

  constructor(private menuService: NbMenuService, private router: Router, private dialogService: NbDialogService,
              private userService: UserService, private chatService: ChatService, private route: ActivatedRoute) {
    this.profile = this.userService.getProfile()
    this.chatService.getAllChat().subscribe({
      next: (models: ChatModel[]) => {
        this.chatModel = models
      },
      error: (e) => {
        console.log("error", e)
      },
    })
    this.chatService.chatModel.subscribe(
      (models: ChatModel[]) => {
        this.chatModel = models
      }
    )
    this.menuListener()
  }

  ngOnInit(): void {

  }

  menuListener() {
    this.menuService.onItemClick()
      .pipe(
        filter(({tag}) => tag === 'chat-list-more'),
        map(({item: {title}}) => title),
      )
      .subscribe((title: string) => {
        switch (title) {
          case 'Profile':
            this.router.navigateByUrl("/profile")
            break;
          case 'New Chat':
            this.dialogService.open(NewChatComponent).onClose.subscribe((closeDialog: CloseDialog) => {
              if (closeDialog?.submit) {
                this.chatService.createChat(closeDialog.data).subscribe({
                  complete: () => {
                    console.log("complete-createFriend");
                  },
                  error: (err) => {
                    console.log("err-createFriend", err);
                  },
                })
              }
            })
            break;
          case 'New Group':
            this.dialogService.open(NewGroupComponent).onClose.subscribe((closeDialog: CloseDialog) => {
              if (closeDialog?.submit) {
                this.chatService.createChatGroup(closeDialog.data).subscribe({
                  complete: () => {
                    console.log("complete-group-chat");
                  },
                  error: (err) => {
                    console.log("err-createFriend", err);
                  },
                })
              }
            })
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
    // this.router.navigate([chatId], {relativeTo: this.route})
    this.router.navigate([chatId], {relativeTo: this.route, skipLocationChange: true})
  }

}
