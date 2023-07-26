import { Component, OnInit } from '@angular/core';
import {MessageService} from "../../_services/message.service";

@Component({
  selector: 'home-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
