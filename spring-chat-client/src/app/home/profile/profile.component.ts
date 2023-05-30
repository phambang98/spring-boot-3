import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from 'src/app/_services/user.service';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {ChatService} from "../../_services/chat.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profile: UserProfile

  isFriend: boolean = false

  constructor(private userService: UserService, private activatedRoute: ActivatedRoute, private router: Router,
              private chatService: ChatService) {
    this.profile = this.userService.getProfile()
    this.activatedRoute.queryParams.subscribe(params => {
      this.isFriend = Boolean(params['isFriend'])
      let friend = this.chatService.getFriend(Number(params['friendId']))
      if (this.isFriend) {
        this.profile.id = friend.userId
        this.profile.email = friend.email
        this.profile.userName = friend.userName
        this.profile.imageUrl = friend.imageUrl
      }
    })
  }

  ngOnInit(): void {
  }

  continue(): void {
    this.router.navigateByUrl("/chat")
  }

  uploadFile(file): void {
    console.log(file)
  }

}
