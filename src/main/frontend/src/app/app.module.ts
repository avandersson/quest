import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


import {AppComponent} from './app.component';
import {QuestComponent} from './quest/quest.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {QuestService} from "./quest/quest.service";
import {LoginComponent} from './login/login.component';
import {FormsModule} from "@angular/forms";
import {AuthService} from "./_services/auth.service";
import {AuthGuard} from "./_guards/auth.guard";
import {AuthInterceptorProvider} from "./_interceptors/auth.interceptor";
import { AdminComponent } from './admin/admin.component';
import {AdminService} from "./admin/admin.service";


@NgModule({
  declarations: [
    AppComponent,
    QuestComponent,
    LoginComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule.forRoot()
  ],
  providers: [
    QuestService,
    AuthService,
    AuthGuard,
    AdminService,
    AuthInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
