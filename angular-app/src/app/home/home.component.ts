import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ChatService} from '../_services/chat.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit ,AfterViewInit{
  constructor(private chatService: ChatService, private router: Router) {
    // this.router.navigateByUrl("/loading")
  }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
  }

}
