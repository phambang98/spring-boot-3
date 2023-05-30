import {AfterViewInit, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NbMenuService, NbDialogService, NbIconLibraries} from '@nebular/theme';
import {filter, map} from 'rxjs/operators';
import {Router, ActivatedRoute} from '@angular/router';
import {UserService} from 'src/app/_services/user.service';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {Observable} from 'rxjs';
import {FriendProfile} from 'src/app/_dtos/chat/FriendProfile';
import {NewChatComponent} from './new-chat/new-chat.component';
import {ChatService} from "../../_services/chat.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'home-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.scss']

})
export class ChatListComponent implements OnInit, AfterViewInit {

  friends: Observable<FriendProfile[]>
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
    this.friends = this.chatService.getFriendsAll()
  }

  ngAfterViewInit() {
    this.friends['_value'].forEach(x => {
      this.chatService.updateStatusFriend(x)
    })
    this.chatService.friendProfileStatus.subscribe((fp: FriendProfile) => {
      if (fp.userId) {
        let newFriend = this.friends['_value'].find(x => x.userId === fp.userId)
        newFriend.lastTimeLogin = fp.lastTimeLogin
        newFriend.status = fp.status
        this.chatService.updateStatusFriend(newFriend)
      }
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
      .subscribe(title => {
        switch (title) {
          case 'Profile':
            this.router.navigateByUrl("/profile")
            break;
          case 'New Chat':
            this.dialogService.open(NewChatComponent).onClose.subscribe((userName) => {
              this.chatService.createFriend(userName).subscribe({
                complete: () => {
                  console.log("complete-createFriend");
                },
                next: (v) => {
                  let newFriend = this.friends['_value'].find(x => x.userId === v.userId)
                  newFriend.lastTimeLogin = v.lastTimeLogin
                  newFriend.status = v.status
                  this.chatService.updateStatusFriend(newFriend)
                },
                error: (err) => {
                  console.log("err-createFriend", err);
                },
              })
            })
            break;
          case 'New Group':

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

  chatClicked(id: Number) {
    this.router.navigate([id], {relativeTo: this.route, skipLocationChange: true})
  }

}
