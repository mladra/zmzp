import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountsRoutingModule } from './accounts-routing.module';
import { AccountsComponent } from './accounts.component';
import { AccountsListComponent } from './accounts-list/accounts-list.component';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { AccountDetilsComponent } from './account-details/account-details.component';
import { FormsModule, NgForm } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    AccountsRoutingModule,
    CommonModule,
    FormsModule
  ],
  declarations: [AccountsComponent, AccountsListComponent, AccountDetilsComponent],
  entryComponents: [AccountDetilsComponent]
})
export class AccountsModule { }
