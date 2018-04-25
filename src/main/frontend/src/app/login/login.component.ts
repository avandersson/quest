import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../_services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};
  loginError = false;

  constructor(private http: HttpClient, private router: Router, private auth: AuthService) {
  }

  ngOnInit() {
  }

  login() {
    this.auth.login(this.model.username, this.model.password)
      .subscribe(() => {
        this.router.navigate(["/"])
      }, error => {
        console.error("Det gick ej att logga in!", error)
      });
    // let headers = new HttpHeaders()
    //   .set('X-Requested-With', 'XMLHttpRequest')
    //   .set('Content-Type', 'application/json')
    //   .set('Cache-Control', 'no-cache');
    // let body = JSON.stringify({
    //     username: this.model.username,
    //     password: this.model.password
    //   });
    //
    // this.http.post<any>('/auth/login', body, {headers: headers}).subscribe(
    //   response => {
    //     localStorage.setItem('wiUser', response.username);
    //     this.router.navigate(['']);
    //   },
    //   error => {
    //     console.error(error);
    //   });
    //
    // this.http.post<any>('/auth/login', body, {headers: headers}).toPromise()
    //   .then(response => {
    //     localStorage.setItem('wiUser', response.username);
    //     this.router.navigate(['']);
    //   }).catch(() => console.error('Failed login!'));

    // .map((response: Response) => {
    //   let user = response.json();
    //   console.debug('user', user);
    //   if (user && user.username) {
    //     // store user details in local storage to keep user logged in between page refreshes
    //     localStorage.setItem('currentUser', JSON.stringify(user));
    //   }
    // });
  }

}
