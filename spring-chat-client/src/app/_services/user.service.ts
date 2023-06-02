import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {UserProfile} from '../_dtos/user/UserProfile';
import {Observable} from 'rxjs';
import {TokenStorageService} from "./token-storage.service";

@Injectable()
export class UserService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient, private tokenStorageService: TokenStorageService) {
  }

  fetchProfile(): Observable<UserProfile> {
    this.httpOptions.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.tokenStorageService.getToken()
    })
    return this.httpClient.get(`${environment.DOMAIN}/api/user/me`, this.httpOptions)
      .pipe(map((user: UserProfile) => {
        this.tokenStorageService.saveUser(user)
        return user
      }))
  }

  getProfile(): UserProfile {
    return this.tokenStorageService.getUser()
  }

  logout(): void {
    this.tokenStorageService.signOut()
    window.location.reload();
  }

  existsByUserName(userName: string): Observable<Boolean> {
    let params = new HttpParams().set("userName", userName)
    return this.httpClient.get(`${environment.DOMAIN}/api/user/exists`, {
      headers: this.httpOptions.headers, params
    }) as Observable<Boolean>;
  }
}
