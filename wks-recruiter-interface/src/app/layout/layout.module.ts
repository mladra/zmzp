import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbDropdownModule, NgbAlertModule, NgbModule, NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { AccountsModule } from "./accounts/accounts.module";
import { PositionsModule } from './positions/positions.module';

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
        PositionsModule,
        NgbAlertModule.forRoot(),
        NgbDropdownModule.forRoot(),
        NgbModule.forRoot()
    ],
    declarations: [LayoutComponent, SidebarComponent, HeaderComponent],
    providers: [AlertsService]
})
export class LayoutModule { }
