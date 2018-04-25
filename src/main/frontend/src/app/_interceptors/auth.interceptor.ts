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
import 'rxjs/add/operator/catch';
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private injector: Injector) {
  }

  authService;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.authService = this.injector.get(AuthService);

    return next.handle(this.addAuthHeader(req, AuthService.getAccessToken()))
      .catch(error => {
        if (error instanceof HttpErrorResponse) {
          switch ((<HttpErrorResponse> error).status) {
            case 401:
              this.authService.logout();
            case 403:
              this.authService.logout();
          }
        }
        return new ErrorObservable(error);
      });
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

}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true,
};
