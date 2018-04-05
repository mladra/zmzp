import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbDropdownModule, NgbAlertModule, NgbModal, NgbModalModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AccountsModule } from './accounts/accounts.module';
import { TestsModule } from './tests/tests.module';

import { LayoutRoutingModule } from './layout-routing.module';
import { LayoutComponent } from './layout.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HeaderComponent } from './components/header/header.component';
import { AlertsService } from '../services/alerts.service';

@NgModule({
    imports: [
        CommonModule,
        LayoutRoutingModule,
        TranslateModule,
        AccountsModule,
        NgbAlertModule.forRoot(),
        NgbDropdownModule.forRoot(),
        NgbModule.forRoot(),
        TestsModule
    ],
    declarations: [LayoutComponent, SidebarComponent, HeaderComponent],
    providers: [AlertsService]
})
export class LayoutModule { }
