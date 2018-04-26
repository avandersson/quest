import {Injectable, Injector} from "@angular/core";
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Router} from "@angular/router";
import {AuthService} from "../_services/auth.service";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/filter";
import "rxjs/add/operator/finally";
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/take";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private ignoreUrls = [
    '/email-verification',
    '/auth/login',
    '/auth/refresh',
    '/auth/create',
    '/auth/login/credentials',
    '/auth/login/email',
    '/auth/login/check-mail',
  ];

  authService;
  refreshingTokens: Map<string, BehaviorSubject<string>> = new Map<string, BehaviorSubject<string>>();

  constructor(private router: Router, private injector: Injector) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.authService = this.injector.get(AuthService);

    return next.handle(this.addAuthHeader(req, AuthService.getAccessToken()))
      .catch(error => {
        if (error instanceof HttpErrorResponse) {
          switch ((<HttpErrorResponse> error).status) {
            case 401:
              return this.handle401(req, next, error);
            case 403:
              return this.handle403(error);
          }
        }
        return new ErrorObservable(error);
      });
  }

  private handle401(req: HttpRequest<any>, next: HttpHandler, error: any): Observable<HttpEvent<any>> {
    // Logout if we receive 401 from /auth/refresh or /auth/login
    if (this.ignoreUrl(req.url)) {
      return this.logout(error);
    }
    // Get the current refresh token
    let id = AuthService.getRefreshToken();

    // If we are not currently refreshing with the client refresh token
    if (!this.refreshingTokens.has(id)) {
      this.refreshingTokens.set(id, new BehaviorSubject<string>(null));

      return this.authService.refresh()
        .switchMap((newAccessToken) => {
          if (newAccessToken) {
            this.refreshingTokens.get(id).next(newAccessToken);
            return next.handle(this.addAuthHeader(req, newAccessToken));
          }
          return this.logout();
        })
        .catch(error => {
          return this.logout(error);
        })
        .finally(() => {
          this.refreshingTokens.delete(id);
        });
    } else {
      // If the current refresh token is being used, wait for the refresh and
      // then execute the request with the new access token.
      return this.refreshingTokens.get(id)
        .filter(token => token != null)
        .take(1)
        .switchMap(token => {
          return next.handle(this.addAuthHeader(req, token));
        });
    }
  }

  private handle403(error: any) {
    this.router.navigate(['/']);
    console.error("Du har inte tillräckligt med rättigheter för att komma åt sidan.");
    return new ErrorObservable(error);
  }

  private addAuthHeader(request: HttpRequest<any>, accessToken: string): HttpRequest<any> {
    if (!accessToken) {
      return request;
    }
    return request.clone({
      setHeaders: {
        "Authorization": 'Bearer ' + accessToken
      }
    });
  }

  private ignoreUrl(url: String): boolean {
    return this.ignoreUrls.filter(ignored => url.endsWith(ignored)).length > 0;
  }

  private logout(error?: any): ErrorObservable {
    this.authService.logout("Din session var inte längre giltig, logga in igen.");
    return new ErrorObservable(error || "");
  }
}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true,
};
