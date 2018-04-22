import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Account } from '../../../entities/account';
import { CurrentUserService } from '../../../services/current-user.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { AccountDetilsComponent } from '../account-details/account-details.component';
import { AccountsService } from '../../../shared/services';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.scss'],
  animations: [routerTransition()],
})
export class AccountsListComponent implements OnInit {

  public users: Account[];
  private current: Account;
  private ngbModalOptions: NgbModalOptions;

  constructor(
    private alertsService: AlertsService,
    private currentUserService: CurrentUserService,
    private accountsService: AccountsService,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.currentUserService.getCurrentUser()
            .subscribe(x => this.current = x);
    this.getAllUsers();

    this.ngbModalOptions = {
      backdrop : 'static',
      keyboard : false
    };
  }

  createAccount() {
    const modalRef = this.modalService.open(AccountDetilsComponent, this.ngbModalOptions);
    modalRef.componentInstance.name = 'Create account';

    const newAccount = new Account();
    newAccount.roles = [];
    newAccount.roles.push('Editor');

    modalRef.componentInstance.setAccount(newAccount, true);
    modalRef.componentInstance.emiter.subscribe(
      account => {
        account.password = null;
        this.users.push(account);
      }
    );
  }

  modifyAccount(account: Account) {
    const modalRef = this.modalService.open(AccountDetilsComponent, this.ngbModalOptions);
    modalRef.componentInstance.name = 'Modify account';

    const accountCopy = new Account();
    accountCopy.id = account.id;
    accountCopy.login = account.login;
    accountCopy.name = account.name;
    accountCopy.surname = account.surname;
    accountCopy.roles = account.roles.copyWithin(account.roles.length, 0, account.roles.length);
    modalRef.componentInstance.setAccount(accountCopy, false);

    modalRef.componentInstance.emiter.subscribe(
      modifiedAccount => {
        const index = this.users.findIndex((a: Account) => a.login === modifiedAccount.login);
        this.users[index] = modifiedAccount;
        this.users[index].password = null;
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
        this.users = data.body as Account[];
      },
      error => {
        console.log(error);
      }
    );
  }

}
