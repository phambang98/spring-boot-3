import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {environment} from '../../environments/environment';
import {SignInRequest} from '../_dtos/auth/SignInRequest';
import {SignUpRequest} from '../_dtos/auth/SignUpRequest';
import {ResultData} from '../_dtos/common/ResultData';
import {TokenVerify} from "../_dtos/auth/TokenVerify";
import {TokenStorageService} from "./token-storage.service";

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

  // getRefreshToken(): string {
  //   return this.tokenStorage.getRefreshToken()
  // }

  setToken(token: string) {
    this.tokenStorage.saveToken(token)
  }

  // setRefreshToken(refreshToken: string) {
  //   this.tokenStorage.saveRefreshToken(refreshToken)
  // }

  login(model: SignInRequest): Observable<ResultData> {
    return this.http.post(`${environment.DOMAIN}/api/account/signin`, model)as Observable<ResultData>
  }

  register(model: SignUpRequest): Observable<ResultData> {
    return this.http.post(`${environment.DOMAIN}/api/account/signup`, model) as Observable<ResultData>
  }

  verifyToken(model: TokenVerify): Observable<ResultData> {
    return this.http.post(`${environment.DOMAIN}/api/account/verify`, model) as Observable<ResultData>
  }

  // refreshToken(refreshToken: string): Observable<SignInResponse> {
  //   return this.http.post(`${environment.DOMAIN}/api/account/refresh-token`, new RefreshTokenRequest(refreshToken), this.httpOptions) as Observable<SignInResponse>
  // }

  logout() {
    this.tokenStorage.signOut()
  }

  getCaptcha(){
    return this.http.get(`${environment.DOMAIN}/api/account/captcha`) as Observable<ResultData>
  }
}
