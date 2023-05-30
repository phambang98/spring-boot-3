import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn} from '@angular/forms';
import {AuthService} from 'src/app/_services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SignUpRequest} from 'src/app/_dtos/auth/SignUpRequest';
import {ApiResponse} from 'src/app/_dtos/common/ApiResponse';
import {NbDialogService} from '@nebular/theme';
import {DialogSuccessComponent} from 'src/app/shared/dialog/dialog-alert/dialog-success.component';
import {
  DialogAuthenticationFailureComponent
} from "../../shared/dialog/dialog-alert/dialog-authentication-failure.component";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  loading: Boolean = false
  signUpFrom: FormGroup

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router,
              private dialogService: NbDialogService, private activatedRoute: ActivatedRoute) {
    this.signUpFrom = this.fb.group({
        userName: ['', Validators.required],
        email: [
          '',
          [
            Validators.required,
            Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,3}$'),
          ],
        ],
        password: ['', [Validators.required]],
        confirmPassword: ['', [Validators.required]],
      }
    )
  }

  ngOnInit(): void {

  }

  register() {
    if (this.signUpFrom.valid) {
      let data = this.signUpFrom.value
      this.loading = true
      this.authService.register(new SignUpRequest(data['userName'], data['email'], data['password'])).subscribe({
          next: (response: ApiResponse) => {
            this.loading = false
            if (!response.success) {
              this.dialogService.open(DialogAuthenticationFailureComponent, {
                context: {title: "SignUp Failure", message: response.message}
              })
            } else {
              this.dialogService.open(DialogSuccessComponent, {
                context: {title: "Congratulation", message: response.message}
              }).onClose.subscribe(() => {
                this.router.navigate(['/auth/signin'], {
                  relativeTo: this.activatedRoute,
                  skipLocationChange: true,
                  queryParams: {userName: data['userName'], password: data['password']}
                });
              })
            }
          },
          error: (err) => {
            this.loading = false
            console.log(err.error.message)
          }
        }
      )
    }
  }
}
