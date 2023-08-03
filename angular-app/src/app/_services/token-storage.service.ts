import {Injectable} from '@angular/core';
import {UserProfile} from '../_dtos/user/UserProfile';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  TOKEN_KEY = 'auth-token';
  USER_KEY = 'auth-user'
  REFRESH_TOKEN_KEY = 'auth-refresh-token';

  constructor() {
  }

  public signOut() {
    localStorage.clear();
  }

  public saveToken(token: string) {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public saveUser(user: UserProfile) {
    localStorage.removeItem(this.USER_KEY);
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  public saveRefreshToken(refreshToken: string): void {
    localStorage.removeItem(this.REFRESH_TOKEN_KEY);
    localStorage.setItem(this.REFRESH_TOKEN_KEY, refreshToken);
  }

  public getRefreshToken(): string {
    return localStorage.getItem(this.REFRESH_TOKEN_KEY);
  }


  public getUser(): UserProfile {
    let raw = JSON.parse(localStorage.getItem(this.USER_KEY));
    return (raw != null) ? new UserProfile(raw['id'], raw['email'], raw['userName'], raw['imageUrl'],) : null

  }

}
