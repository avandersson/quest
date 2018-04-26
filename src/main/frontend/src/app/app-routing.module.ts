import {NgModule}             from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {QuestComponent} from "./quest/quest.component";
import {LoginComponent} from "./login/login.component";
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
    {path: '', component: QuestComponent},
    {path: 'login', component: LoginComponent},
    {path: 'quest', component: QuestComponent},
    {path: 'admin', component: AdminComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
