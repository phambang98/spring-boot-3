import {Component, OnInit} from '@angular/core';
import {ChatService} from 'src/app/_services/chat.service';
import {Router} from '@angular/router';
import {UserService} from 'src/app/_services/user.service';
import {ChatModel} from "../../_dtos/chat/ChatModel";
import {MessageService} from "../../_services/message.service";
import {ErrorService} from "../../_services/error.service";

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit {
  progress = 0;

  constructor(private chatService: ChatService, private router: Router, private userService: UserService,
              private messageService: MessageService, private errorService: ErrorService) {
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

    this.chatService.fetchChats().subscribe({
      complete: () => {
        this.progress = 80;
        this.chatService.updateFetch(80)
      },
      next: (chatModels: ChatModel[]) => {
        (async () => {
          if (chatModels.length != 0) {
            this.chatService.fetchChat(chatModels)
            this.messageService.fetchMessages(chatModels[0].chatId).subscribe({
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
