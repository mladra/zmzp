import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { routerTransition } from '../router.animations';
import { Account } from '../entities/account';
import { AccountsService } from '../shared/services';
import { Router } from '@angular/router';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.scss'],
    animations: [routerTransition()]
})
export class SignupComponent implements OnInit {

    @Output() emiter: EventEmitter<Account> = new EventEmitter<Account>();

    errorMessage: string;
    private account: Account;
    private repeatPassword: string;

    constructor(
        private accountService: AccountsService,
        private router: Router,
    ) {}

    ngOnInit() {
        this.account = new Account();
    }

    submit() {
        if (this.account.password !== this.repeatPassword) {
            return;
        }
        this.accountService.register(this.account).subscribe(
            response => {
                this.router.navigate(['/login'], { queryParams: { signup: true } });
            },
            error => {
                this.errorMessage = error.error;
            }
        );
    }

}
