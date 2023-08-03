import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ChatService} from '../_services/chat.service';
import {Router} from '@angular/router';
import {UserService} from "../_services/user.service";
import {ErrorService} from "../_services/error.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit ,AfterViewInit{
  constructor(private userService: UserService,private errorService :ErrorService, private router: Router) {
    this.userService.fetchProfile().subscribe({
      complete: () => {
      },
      error: (e) => {
        this.errorService.errorFetch(e)
      },
    })
  }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {

  }

}
