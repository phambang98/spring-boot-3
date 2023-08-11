import {Component, OnInit} from '@angular/core';
import {ChatModel} from "../../_dtos/chat/ChatModel";
import {ChatService} from "../../_services/chat.service";
import {Router} from "@angular/router";
import {UserService} from "../../_services/user.service";
import {MessageService} from "../../_services/message.service";
import {ErrorService} from "../../_services/error.service";

@Component({
  selector: 'app-chat-banner',
  templateUrl: './chat-banner.component.html',
  styleUrls: ['./chat-banner.component.scss']
})
export class ChatBannerComponent implements OnInit {

  constructor(private chatService: ChatService, private router: Router, private userService: UserService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.chatService.fetchChats().subscribe({
      next: (chatModels: ChatModel[]) => {
        (async () => {
          if (chatModels.length != 0) {
            this.chatService.fetchChat(chatModels)
            this.messageService.fetchMessages(chatModels[0].chatId).subscribe({
              error: (e) => {
                console.log("error", e)
              },
            })
          }
        })();
      },
      error: (e) => {
        console.log("error", e)
      },
    })
  }

}
