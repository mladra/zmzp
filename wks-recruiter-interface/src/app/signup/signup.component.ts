import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { routerTransition } from '../router.animations';
import { Account } from '../entities/account';
import { AccountsService } from '../shared/services';
import { AlertsService } from '../services/alerts.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.scss'],
    animations: [routerTransition()]
})
export class SignupComponent implements OnInit {

    @Output() emiter: EventEmitter<Account> = new EventEmitter<Account>();

    private account: Account;

    constructor(
        private accountService: AccountsService,
        private alertsService: AlertsService
    ) { }

    ngOnInit() {
        this.account = new Account();
    }

    submit() {
        this.accountService.register(this.account).subscribe(
            response => {
                this.alertsService.addAlert('success', 'Successfully created account with email ' + this.account.login);
                // this.emiter.emit(this.account);
                // console.log(response);
            },
            error => {
                this.alertsService.addAlert('danger', error.error);
            }
        );
        // this.positionsService.addPosition(this.position).subscribe(
        // response => {
        // this.alertsService.addAlert('success', 'New position: ' + this.position.name + ' added successfully.');
        // this.emitter.emit(true);
        // },
        // error => {
        // this.alertsService.addAlert('danger', error.error);
        // }
        // )
    }

}
