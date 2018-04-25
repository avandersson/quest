import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import 'rxjs/add/operator/do'
import * as jwt_decode from "jwt-decode";
import {AuthResponse} from "../_models/auth.response";

const ACCESS_TOKEN_NAME: string = "access_token";

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
        }
      });
  }

  logout() {
    localStorage.removeItem(ACCESS_TOKEN_NAME);
    this.router.navigate(["login"]);
  }

  static getAccessToken(): string {
    return localStorage.getItem(ACCESS_TOKEN_NAME);
  }

  static getCurrentUser(): string {
    return this.getAccessToken() ? jwt_decode(this.getAccessToken()).sub : '';
  }
}
