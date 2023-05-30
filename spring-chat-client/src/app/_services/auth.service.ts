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

  setToken(token: string) {
    this.tokenStorage.saveToken(token)
  }

  login(model: SignInRequest): Observable<SignInResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/signin`, model, this.httpOptions)
      .pipe(map((response: SignInResponse) => {
        this.tokenStorage.saveToken(response.accessToken)
        this.tokenStorage.saveUser(new UserProfile(response.id, response.email, response.userName, response.imageUrl))
        return response
      }));
  }

  register(model: SignUpRequest): Observable<ApiResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/signup`, model, this.httpOptions) as Observable<ApiResponse>;
  }

  verifyToken(model: TokenVerify): Observable<ApiResponse> {
    return this.http.post(`${environment.DOMAIN}/api/account/verify`, model, this.httpOptions) as Observable<ApiResponse>;
  }

  logout() {
    this.tokenStorage.signOut()
  }
}
