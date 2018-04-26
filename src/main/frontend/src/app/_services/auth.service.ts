import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import 'rxjs/add/operator/do'
import 'rxjs/add/operator/map'
import * as jwt_decode from "jwt-decode";
import {AuthResponse} from "../_models/auth.response";
import {Observable} from "rxjs/Observable";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

const ACCESS_TOKEN_NAME: string = "access_token";
const REFRESH_TOKEN_NAME: string = "refresh_token";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient, private router: Router) {}

  login(username: String, password: String) {
    let headers = new HttpHeaders()
      .set('X-Requested-With', 'XMLHttpRequest')
      .set('Content-Type', 'application/json')
      .set('Cache-Control', 'no-cache');
    return this.http.post<AuthResponse>('/auth/login', JSON.stringify({
      username: username,
      password: password
    }), {headers: headers})
      .do((response) => {
        if (response && response.accessToken) {
          localStorage.setItem(ACCESS_TOKEN_NAME, response.accessToken);
          localStorage.setItem(REFRESH_TOKEN_NAME, response.refreshToken);
        }
      });
  }

  refresh(): Observable<string> {
    let refreshToken = AuthService.getRefreshToken();
    if (refreshToken) {
      let headers = new HttpHeaders();
      headers.append('Content-Type', 'application/json');
      headers.append('Cache-Control', 'no-cache');
      return this.http.post<AuthResponse>(
        '/auth/refresh',
        refreshToken,
        {headers: headers})
        .do(response => {
            AuthService.removeTokens();
            if (response && response.accessToken) {
              localStorage.setItem(ACCESS_TOKEN_NAME, response.accessToken);
              response.refreshToken && localStorage.setItem(REFRESH_TOKEN_NAME, response.refreshToken);
            }
          },
          () => {
            AuthService.removeTokens();
          }
        )
        .map(response => response.accessToken);
    }
    return new ErrorObservable("No refresh token present!");
  }

  logout() {
    AuthService.removeTokens();
    this.router.navigate(["login"]);
  }

  static getAccessToken(): string {
    return localStorage.getItem(ACCESS_TOKEN_NAME);
  }

  static getRefreshToken(): string {
    return localStorage.getItem(REFRESH_TOKEN_NAME);
  }

  static getCurrentUser(): string {
    return this.getAccessToken() ? jwt_decode(this.getAccessToken()).sub : '';
  }

  static removeTokens() {
    localStorage.removeItem(ACCESS_TOKEN_NAME);
    localStorage.removeItem(REFRESH_TOKEN_NAME);
  }
}
