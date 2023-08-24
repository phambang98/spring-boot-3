import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ChatService} from '../_services/chat.service';
import {Router} from '@angular/router';
import {UserService} from "../_services/user.service";
import {ErrorService} from "../_services/error.service";
import {MatTabChangeEvent} from "@angular/material/tabs";

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
        console.log("error", e)
      },
    })
  }

  onChangeTab(event:any): void {
    switch (event.tabTitle) {
      case 'Chat':
        this.router.navigate(['/chat'])
        break
      case 'Lucky wheel':
        this.router.navigate(['/lucky-wheel'])
        break
      case 'Log out':
        this.userService.logout()
        this.router.navigateByUrl("/auth")
        break
    }
  }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {

  }

}
