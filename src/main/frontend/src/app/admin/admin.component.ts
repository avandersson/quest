import { Component, OnInit } from '@angular/core';
import {AdminService} from "./admin.service";
import {Account} from "../_models/account";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  accounts: Array<Account> = [];

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getAccounts().then( accounts => this.accounts = accounts)
  }

}
