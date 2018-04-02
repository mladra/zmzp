import { Component, OnInit, TemplateRef } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Account } from '../../../entities/account';
import { CurrentUserService } from '../../../services/current-user.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountDetilsComponent } from '../account-details/account-details.component';
import { AccountsService } from '../../../shared/services';
import { LocationStrategy, PathLocationStrategy } from '@angular/common';
import { Local } from 'protractor/built/driverProviders';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.scss'],
  animations: [routerTransition()],
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
    modalRef.componentInstance.name = 'Create account';

    const newAccount = new Account();
    newAccount.roles = new Array<string>();
    newAccount.roles.push('Editor');

    modalRef.componentInstance.setAccount(newAccount, true);
    modalRef.componentInstance.emiter.subscribe(
      account => {
        this.users.push(account);
      }
    );
  }

  modifyAccount(account) {
    const modalRef = this.modalService.open(AccountDetilsComponent);
    modalRef.componentInstance.name = 'Modify account';
    modalRef.componentInstance.setAccount(account, false);
    modalRef.componentInstance.emiter.subscribe(
      modifiedAccount => {
        console.log(modifiedAccount);
      }
    );
  }

  deleteAccount(account: Account) {
    this.accountsService.deleteAccount(account.login).subscribe(
      response => {
        this.alertsService.addAlert('success', 'Successfully deleted account with email: ' + account.login);

        const index = this.users.indexOf(account);
        if (index > -1) {
          this.users.splice(index, 1);
        }
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
