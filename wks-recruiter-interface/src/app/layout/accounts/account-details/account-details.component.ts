import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from '../../../entities/account';
import { AccountsService } from '../../../shared/services';
import { AlertsService } from '../../../services/alerts.service';

@Component({
    selector: 'app-account-details-component',
    templateUrl: './account-details.component.html'
})
export class AccountDetilsComponent implements OnInit {

    public account: Account;
    public createAccount: boolean;
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
        this.activeModal.close();
        if (this.createAccount) {
            this.accountService.createAccount(this.account).subscribe(
                response => {
                    this.alertsService.addAlert('success', 'Successfully created account with email ' + this.account.login);
                },
                error => {
                    this.alertsService.addAlert('danger', error.error);
                }
            );
        } else {
            this.accountService.editAccount(this.account).subscribe(
                response => {
                    this.alertsService.addAlert('success', 'Successfully edited account with email ' + this.account.login);
                },
                error => {
                    this.alertsService.addAlert('danger', error.error);
                }
            );
        }
    }

    checkIfPasswordsMatch() {
        return this.account.password !== this.confirm;
    }
}
