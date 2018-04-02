import { Component, OnInit, TemplateRef } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Account } from '../../../entities/account';
import { CurrentUserService } from '../../../services/current-user.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountDetilsComponent } from '../account-details/account-details.component';
import { AccountsService } from '../../../shared/services';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.scss'],
  animations: [routerTransition()]
})
export class AccountsListComponent implements OnInit {

  public users: Account[];
  private current: Account;

  constructor(
    private alertsService: AlertsService,
    private currentUserService: CurrentUserService,
    private accountsService: AccountsService,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.currentUserService.getCurrentUser()
            .subscribe(x => this.current = x);
    this.accountsService.getAll().subscribe(
      data => {
        let userString = JSON.stringify(data.body);
        this.users = JSON.parse(userString);
      },
      error => {
        console.log(error);
      }
    )
  }

  modifyAccount(account) {
    const modalRef = this.modalService.open(AccountDetilsComponent);
    modalRef.componentInstance.name = 'User details';
    modalRef.componentInstance.account = account;
    modalRef.componentInstance.createAccount = false;
  }

  deleteAccount(id: String) {
    //TODO: to implement
  }

}
