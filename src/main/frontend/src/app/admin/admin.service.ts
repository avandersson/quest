import {Injectable, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Account} from "../_models/account";

@Injectable()
export class AdminService {

  accounts: Array<Account> = [];

  constructor(private http: HttpClient) {}

  getAccounts(): Promise<Array<Account>> {
    return this.http.get<Array<Account>>("/api/admin/accounts").toPromise()
      .then( accounts => {
        return accounts;
      })
  }

  getTokens() {

  }
}
