import { LoginResponse } from './../auth/login/login-response.model';
import { AuthService } from './../auth/shared/auth.service';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, switchMap, filter, take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor{
  isTokenRefreshing = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(private authService: AuthService){
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwtToken = this.authService.getToken();
    if (req.url.indexOf('auth') !== -1) {
      return next.handle(req);
    }
    if (jwtToken) {
      return next.handle(this.addToken(req, jwtToken))
              .pipe(catchError(error => {
                if (error instanceof HttpErrorResponse && error.status === 403) {
                  return this.handleAuthError(req, next);
                } else {
                  return throwError(error);
                }
              }));
    }
    return next.handle(req);
  }

  // add the token request
  private addToken(req: HttpRequest<any>, jwtToken: any) {
    return req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
    });
  }

  // request new token using refresh token
  private handleAuthError(req: HttpRequest<any>,  next: HttpHandler) {
    // while calling to refresh token service, others can not interact with backend
    if (!this.isTokenRefreshing) {
      this.isTokenRefreshing = true;
      this.refreshTokenSubject.next(null);
      return this.authService.refeshToken().pipe(
        switchMap((refreshTokenResponse: LoginResponse) => {
          this.isTokenRefreshing = false;
          this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);
          return next.handle(this.addToken(req, refreshTokenResponse.authenticationToken));
        })
      );
    } else {
      return this.refreshTokenSubject.pipe(
        filter(result => result !== null),
        take(1),
        switchMap((res) => {
            return next.handle(this.addToken(req,
              this.authService.getToken()));
        })
    );
    }
  }
}
