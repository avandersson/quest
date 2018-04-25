import {Component, OnInit} from '@angular/core';
import {QuestService} from "./quest.service";
import {Quest} from "../_models/quest";
import {AuthService} from "../_services/auth.service";

@Component({
  selector: 'app-quest',
  templateUrl: './quest.component.html',
  styleUrls: ['./quest.component.scss']
})
export class QuestComponent implements OnInit {
  isCollapsed: boolean;
  quests: Array<Quest> = [];

  constructor(private service: QuestService) {
    this.isCollapsed = true;
  }

  ngOnInit() {
    this.quests = [];
    // this.service.getQuests().toPromise().then( data => {
    //
    // });

    this.service.getQuests().then(data => {
        console.log("Adding quest!");
        this.quests = data;
      });
  }
}
