import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/_services/auth.service';
import {UserService} from 'src/app/_services/user.service';
import {UserProfile} from 'src/app/_dtos/user/UserProfile';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrls: ['./token.component.scss']
})
export class TokenComponent implements OnInit {

  loading: Boolean = true
  profile: UserProfile
  accessToken: string
  // refreshToken: string

  constructor(private route: ActivatedRoute, private authService: AuthService, private userService: UserService, private router: Router) {
    this.route.queryParams.subscribe(params => {
      this.accessToken = params['accessToken']
      // this.refreshToken = params['refreshToken']
      if (!this.accessToken) {
        if (!this.userService.getProfile()) {
          this.router.navigateByUrl("/auth/signin")
        }
      } else {
        this.authService.setToken(this.accessToken)
        // this.authService.setRefreshToken(this.refreshToken)
      }
    });
  }

  ngOnInit(): void {
    (async () => {
      await delay(5000);
      this.userService.fetchProfile().subscribe({
        next: (profile: UserProfile) => {
          this.profile = profile
          this.loading = false
        },
        error: (e) => {
          this.router.navigateByUrl("/auth/signin")
        },
      })
    })();
  }

  continue() {
    this.router.navigateByUrl("/")
  }
}
