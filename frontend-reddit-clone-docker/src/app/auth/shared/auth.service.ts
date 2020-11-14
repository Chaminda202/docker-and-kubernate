import { LoginResponse } from './../login/login-response.model';
import { LoginRequest } from './../login/login-request.model';
import { SingUpRequest } from './../sign-up/sing-up-request.model';
import { environment } from './../../../environments/environment';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl: string = environment.baseUrl;
  private singupContext = '/auth/signup';
  private loginContext = '/auth/login';
  private refreshTokenContext = '/auth/refresh/token';
  private logoutContext = '/auth/logout';

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService ) { }

  singUp(singUpRequest: SingUpRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}${this.singupContext}`, singUpRequest);
  }

  // without using ngx-webstorage
  /* login(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}${this.loginContext}`, loginRequest)
          .pipe(
            map(data => {
              localStorage.setItem('loginResponse', JSON.stringify(data));
              return true;
            })
          );
  } */

  login(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}${this.loginContext}`, loginRequest)
          .pipe(
            map((data: LoginResponse) => {
              this.localStorage.store('username', data.username);
              this.localStorage.store('authenticationToken', data.authenticationToken);
              this.localStorage.store('refreshToken', data.refreshToken);
              this.localStorage.store('expiredAt', data.expiredAt);

              this.loggedIn.emit(true);
              this.username.emit(data.username);
              return true;
            })
          );
  }

  refeshToken(): Observable<any> {
    const refreshTokenPayload = {
      username: this.getUsername(),
      refreshToken: this.getRefreshToken()
    };
    return this.httpClient.post<LoginResponse>(`${this.apiUrl}${this.refreshTokenContext}`,
          refreshTokenPayload)
            .pipe(
              tap(response => {
                this.localStorage.store('authenticationToken', response.authenticationToken);
                this.localStorage.store('expiredAt', response.expiredAt);
              })
            );
  }

  logout(): Observable<any> {
    const logoutPayload = {
      username: this.getUsername(),
      refreshToken: this.getRefreshToken()
    };
    return this.httpClient.post(`${this.apiUrl}${this.logoutContext}`, logoutPayload);
  }

  getToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getUsername() {
    return this.localStorage.retrieve('username');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getExpiredAt() {
    return this.localStorage.retrieve('expiredAt');
  }

  isLoggedIn() {
    return this.getToken() !== null;
  }
  clearTokenDetails() {
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiredAt');
  }
}
