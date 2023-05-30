import {Component, OnInit} from '@angular/core';
import {ChatService} from 'src/app/_services/chat.service';
import {Router} from '@angular/router';
import {UserService} from 'src/app/_services/user.service';
import {FriendProfile} from "../../_dtos/chat/FriendProfile";
import {MessageService} from "../../_services/message.service";
import {TokenStorageService} from "../../_services/token-storage.service";
import {ErrorService} from "../../_services/error.service";

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit {
  progress = 0;

  constructor(private chatService: ChatService, private router: Router, private userService: UserService,
              private messageService: MessageService, private errorService: ErrorService, private tokenStorageService: TokenStorageService) {
  }


  ngOnInit(): void {
    this.progress = 10;
    this.chatService.updateFetch(10)
    this.userService.fetchProfile().subscribe({
      complete: () => {
        this.progress = 20;
        this.chatService.updateFetch(20)
      },
      error: (e) => {
        this.errorService.errorFetch(e)
      },
    })

    this.chatService.fetchFriends().subscribe({
      complete: () => {
        this.progress = 80;
        this.chatService.updateFetch(80)
      },
      next: (friends: FriendProfile[]) => {
        (async () => {
          if (friends.length != 0) {
            this.chatService.fetchFriend(friends)
            this.messageService.fetchMessages(friends[0].userId).subscribe({
              error: (e) => {
                this.errorService.errorFetch(e)
              },
            })
          }
          this.progress = 100;
          this.chatService.updateFetch(100)
        })();
      },
      error: (e) => {
        this.errorService.errorFetch(e)
      },
    })
  }


}
