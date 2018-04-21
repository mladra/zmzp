import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from '../../../entities/account';
import { AccountsService } from '../../../shared/services';
import { AlertsService } from '../../../services/alerts.service';

@Component({
    selector: 'app-account-details-component',
    templateUrl: './account-details.component.html',
    styles: ['./account-details.component.scss'],
})
export class AccountDetilsComponent implements OnInit {

    @Output() emiter: EventEmitter<Account> = new EventEmitter<Account>();

    public account: Account;
    public createAccount: boolean;
    private title: string;
    private roles: Array<String>;
    private confirm: string;

    constructor(
        public activeModal: NgbActiveModal,
        private accountService: AccountsService,
        private alertsService: AlertsService) { }

    ngOnInit(): void {
        this.roles = ['Moderator', 'Editor', 'Candidate'];
    }

    close() {
        this.activeModal.close();
    }

    submit() {
        if (this.checkIfPasswordsDiffers()) {
            return;
        }

        if (this.createAccount) {

            this.accountService.createAccount(this.account).subscribe(
                response => {
                    this.alertsService.addAlert('success', 'Successfully created account with email ' + this.account.login);
                    this.emiter.emit(this.account);
                },
                error => {
                    this.alertsService.addAlert('danger', error.error);
                }
            );
            this.activeModal.close();
        } else {
            this.accountService.editAccount(this.account).subscribe(
                response => {
                    this.alertsService.addAlert('success', 'Successfully edited account with email ' + this.account.login);
                    this.emiter.emit(this.account);
                },
                error => {
                    this.alertsService.addAlert('danger', error.error);
                }
            );
            this.activeModal.close();
        }
    }

    checkIfPasswordsDiffers() {
        if (this.createAccount) {
            return this.account.password !== this.confirm;
        }

        return this.account.password ? this.account.password !== this.confirm : false;
    }

    setAccount(account, createAccount) {
        this.account = account;
        this.createAccount = createAccount;
        if (this.createAccount) {
            this.title = 'Create account';
        } else {
            this.title = this.account.login + ' details';
        }
    }
}
