import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';

import {environment} from '../../environments/environment';
import {AuthService} from '../_services/auth.service';
import {TokenStorageService} from "../_services/token-storage.service";
import {SignInResponse} from "../_dtos/auth/SignInResponse";
import {Router} from "@angular/router";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService, private router: Router, private tokenStorageServices: TokenStorageService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if user is logged in and request is to the api url
    const token = this.authService.getToken();
    const isApiUrl = request.url.startsWith(environment.DOMAIN);
    if (token && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
    }
    return next.handle(request).pipe(
      (catchError(err => {
          if (err.status === 401) {
            this.authService.logout()
            this.router.navigateByUrl("/auth/signin")
            // return this.handle401Error(request, next);
          }
          const error = err.error.message || err.statusText;
          return throwError(error);
        }
      )));
  }

  // private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
      // if (this.authService.getRefreshToken()) {
      // return this.authService.refreshToken(this.authService.getRefreshToken()).pipe(
      //   switchMap((res: SignInResponse) => {
      //     this.tokenStorageServices.saveToken(res.accessToken)
          // this.tokenStorageServices.saveRefreshToken(res.refreshToken)
          // return next.handle(request.clone({headers: request.headers.set("Authorization", "Bearer " + res.accessToken)}));
        // }),
        // catchError((err) => {
        //   this.authService.logout()
        //   this.router.navigateByUrl("/auth/signin")
        //   return throwError(err);
        // })
      // );
    // } else {
    //   this.authService.logout()
    //   this.router.navigateByUrl("/auth/signin")
    // }
  // }
}
