import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Quest} from "../_models/quest";
import {Router} from "@angular/router";

@Injectable()
export class QuestService {

  constructor(private http: HttpClient, private router: Router) {}

  getQuests(): Promise<Array<Quest>> {
    let headers = new HttpHeaders()
      .set('X-Requested-With', 'XMLHttpRequest');
    return this.http.get<Array<Quest>>('/api/quests', {headers: headers}).toPromise()
      .then(data => {
        return data;
      });
  }

  private static handleError(error: any): Promise<any> {
    console.error(error.status);
    if (error.status == 403 || error.status == 401) {
      localStorage.removeItem('wiUser');
      window.location.href = '/login';
      // return this.router.navigate(['/login']);
    }
    if (error.status == 400 || error.status == 500) {
      return Promise.reject(error.json().errorMessage);
    }
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
