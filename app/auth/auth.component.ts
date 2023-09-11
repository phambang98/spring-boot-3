import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Router} from '@angular/router';
import {ResultData} from "../_dtos/common/ResultData";
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
        next: (v: ResultData) => {
          if (v.success) {
            this.router.navigateByUrl("/")
          } else {
            console.log(v.message)
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
