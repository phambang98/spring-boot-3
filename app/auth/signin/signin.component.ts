import {Component, OnInit} from '@angular/core';
import {AuthService} from 'src/app/_services/auth.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SignInRequest} from 'src/app/_dtos/auth/SignInRequest';
import {Router, ActivatedRoute} from '@angular/router';
import {environment} from '../../../environments/environment';
import {SignInResponse} from "../../_dtos/auth/SignInResponse";
import {ResultData} from "../../_dtos/common/ResultData";
import {UserProfile} from "../../_dtos/user/UserProfile";
import {TokenStorageService} from "../../_services/token-storage.service";
import {
  DialogAuthenticationFailureComponent
} from "../../shared/dialog/dialog-alert/dialog-authentication-failure.component";
import {NbDialogService} from "@nebular/theme";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {

  loading: Boolean = false
  signInFrom: FormGroup
  redirect = "/"

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router, private dialogService: NbDialogService,
              private activatedRoute: ActivatedRoute, private tokenStorage: TokenStorageService) {
    this.signInFrom = this.fb.group({
      userName: [],
      password: [],
    })

    this.activatedRoute.queryParams.subscribe(params => {
      let userName = params['userName']
      let password = params['password']
      if (params['returnUrl']) {
        this.redirect = params['returnUrl']
      }
      if (userName && password) {
        this.signInFrom.setValue({
          userName: userName,
          password: password,
        });
      }
    })
  }

  ngOnInit(): void {
  }

  login() {
    if (this.signInFrom.valid) {
      let data = this.signInFrom.value
      this.loading = true
      this.authService.login(new SignInRequest(data['userName'], data['password'])).subscribe({
        complete: () => {

          this.loading = false
        },
        next: (resultData: ResultData) => {
          if (resultData.success) {
            this.tokenStorage.saveToken(resultData.data.accessToken)
            // this.tokenStorage.saveRefreshToken(resultData.data.refreshToken)
            this.tokenStorage.saveUser(new UserProfile(resultData.data.id, resultData.data.email, resultData.data.userName, resultData.data.imageUrl))
            this.router.navigateByUrl(this.redirect)
          } else {
            this.dialogService.open(DialogAuthenticationFailureComponent, {
              context: {title: "Login Failure", message: resultData.message}
            })
          }
        },
        error: (e) => {
          this.loading = false
        },
      })
    }
  }

  facebook() {
    window.location.href = `${environment.DOMAIN}/oauth2/authorization/facebook?redirect_url=http://localhost:4200/auth/token`;
  }

  github() {
    window.location.href = `${environment.DOMAIN}/oauth2/authorization/github?redirect_url=http://localhost:4200/auth/token`;
  }

  google() {
    window.location.href = `${environment.DOMAIN}/oauth2/authorization/google?redirect_url=http://localhost:4200/auth/token`;
  }

}
