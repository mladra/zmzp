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
    this.getAllUsers();
  }

  createAccount() {
    const modalRef = this.modalService.open(AccountDetilsComponent);
    modalRef.result.then(
      d => {
        console.log(d);
        this.getAllUsers();
    },
      error => {
        console.log(error);
    });
    modalRef.componentInstance.name = 'Create account';
    const newAccount = new Account();
    newAccount.roles = new Array<string>();
    modalRef.componentInstance.setAccount(newAccount, true);
  }

  modifyAccount(account) {
    const modalRef = this.modalService.open(AccountDetilsComponent);
    modalRef.result.then(
      data => {
        console.log(data);
        this.getAllUsers();
    },
      error => {
        console.log(error);
    });
    modalRef.componentInstance.name = 'Modify account';
    modalRef.componentInstance.setAccount(account, false);
  }

  deleteAccount(login: String) {
    this.accountsService.deleteAccount(login).subscribe(
      response => {
        this.alertsService.addAlert('success', 'Successfully deleted account with id ${login}');
      },
      error => {
        this.alertsService.addAlert('danger', 'Error occurred during deleting user.');
      }
    );
  }

  getAllUsers() {
    this.accountsService.getAll().subscribe(
      data => {
        const userString = JSON.stringify(data.body);
        this.users = JSON.parse(userString);
      },
      error => {
        console.log(error);
      }
    );
  }

}
