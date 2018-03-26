import { Component, OnInit } from '@angular/core';
import { AlertsService } from "../services/alerts.service";

@Component({
    selector: 'app-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
    constructor(private alertService: AlertsService) {
        this.alerts = this.alertService.getAlerts();
    }
    public alerts: any[];
    ngOnInit() {
    }
    closeAlert(alert: any) {
        this.alertService.closeAlert(alert);
    }
}
