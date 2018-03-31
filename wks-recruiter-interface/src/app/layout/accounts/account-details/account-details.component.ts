import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from '../../../entities/account';

@Component({
    selector: 'app-account-details-component',
    templateUrl: './account-details.component.html'
})
export class AccountDetilsComponent implements OnInit {

    public account: Account;
    private accountName: string;
    private roles: Array<String>;

    constructor(public activeModal: NgbActiveModal) { }

    ngOnInit(): void {
        this.roles = ['Moderator', 'Publisher', 'Candidate'];
    }

    close() {
        this.activeModal.close();
    }

    submit() {
        this.activeModal.close();
        console.log(this.account);
    }
}
