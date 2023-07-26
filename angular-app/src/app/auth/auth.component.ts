import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Router} from '@angular/router';
import {ApiResponse} from "../_dtos/common/ApiResponse";
import {TokenVerify} from "../_dtos/auth/TokenVerify";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private tokenStorageService: TokenStorageService) {
    if (this.authService.getToken()) {
      this.authService.verifyToken(new TokenVerify(this.tokenStorageService.getToken())).subscribe({
        next: (v: ApiResponse) => {
          if (v.success) {
            this.router.navigateByUrl("/")
          } else {
            this.tokenStorageService.signOut()
            this.router.navigateByUrl("/auth/signin")
          }
        }, error: (e) => {
          console.log("verifyToken", e)
        }
      })
    }

  }

  ngOnInit(): void {
  }

}
