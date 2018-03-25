import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Account } from "../../../entities/account";
import { CurrentUserService } from "../../../services/current-user.service";

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.scss'],
  animations: [routerTransition()]
})
export class AccountsListComponent implements OnInit {

  public users: Account[];
  private current: Account;

  constructor(private currentUserService: CurrentUserService) { }

  ngOnInit() {
    this.currentUserService.getCurrentUser()
            .subscribe(x => this.current = x);
    this.users = [this.current, this.current, this.current, this.current, this.current];
  }

}
