import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {SignInResponse} from '../_dtos/auth/SignInResponse';
import {SignInRequest} from '../_dtos/auth/SignInRequest';
import {SignUpRequest} from '../_dtos/auth/SignUpRequest';
import {ApiResponse} from '../_dtos/common/ApiResponse';
import {UserProfile} from '../_dtos/user/UserProfile';
import {TokenVerify} from "../_dtos/auth/TokenVerify";
import {TokenStorageService} from "./token-storage.service";
import {RefreshTokenRequest} from "../_dtos/auth/RefreshTokenRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {

  }

  getToken(): string {
    return this.tokenStorage.getToken()
  }

  getRefreshToken(): string {
    return this.tokenStorage.getRefreshToken()
  }

  setToken(token: string) {
    this.tokenStorage.saveToken(token)
  }

  setRefreshToken(refreshToken: string) {
    this.tokenStorage.saveRefreshToken(refreshToken)
  }

  login(model: SignInRequest): Observable<SignInResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/signin`, model)
      .pipe(map((response: SignInResponse) => {
        this.tokenStorage.saveToken(response.accessToken)
        this.tokenStorage.saveRefreshToken(response.refreshToken)
        this.tokenStorage.saveUser(new UserProfile(response.id, response.email, response.userName, response.imageUrl))
        return response
      }));
  }

  register(model: SignUpRequest): Observable<ApiResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/signup`, model) as Observable<ApiResponse>;
  }

  verifyToken(model: TokenVerify): Observable<ApiResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/verify`, model) as Observable<ApiResponse>;
  }

  refreshToken(refreshToken: string): Observable<SignInResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/refresh-token`, new RefreshTokenRequest(refreshToken), this.httpOptions) as Observable<SignInResponse>;
  }

  logout() {
    this.tokenStorage.signOut()
  }
}
