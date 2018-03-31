import { Component, OnInit, TemplateRef } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Account } from '../../../entities/account';
import { CurrentUserService } from '../../../services/current-user.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountDetilsComponent } from '../account-details/account-details.component';

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
    private modalService: NgbModal) { }

  ngOnInit() {
    this.currentUserService.getCurrentUser()
            .subscribe(x => this.current = x);
    this.users = [this.current, this.current, this.current, this.current, this.current];
  }

  modifyAccount(account) {
    const modalRef = this.modalService.open(AccountDetilsComponent);
    modalRef.componentInstance.name = 'User details';
    modalRef.componentInstance.account = account;
  }

  deleteAccount(id: String) {
    this.alertsService.addAlert('success', 'Account ' + id + ' has been removed');
  }

}
