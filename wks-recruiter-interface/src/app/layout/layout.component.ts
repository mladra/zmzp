import { Component, OnInit } from '@angular/core';
import { AlertsService } from "../services/alerts.service";
import { Account } from '../entities/account';
import { CurrentUserService } from '../services/current-user.service';

@Component({
    selector: 'app-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
    constructor(private currentUserService: CurrentUserService, private alertService: AlertsService) {
        this.alerts = this.alertService.getAlerts();
    }
    public alerts: any[];
    private myDocument: any;
    ngOnInit() {
        this.myDocument = document;
        this.myDocument.keyupEventFunction = event => event.preventDefault();
        this.myDocument.printscrEventFunction = event => function (event) {
            if (event.keyCode == 44) {
                var sound: any = document.getElementById("error-audio");
                sound.play();
                var aux = document.createElement("input");
                aux.setAttribute("value", "Print Screen Prohibited!");
                document.body.appendChild(aux);
                aux.select();
                document.execCommand("copy");
                document.body.removeChild(aux);
                alert("Print Screen Prohibited!");
            }
        };
        this.currentUserService.getCurrentUser()
            .subscribe(x => this.makeKeyPermissons(x));
    }
    makeKeyPermissons(user: Account) {
        document.removeEventListener('keyup', this.myDocument.keyupEventFunction)
        if (user.roles.indexOf("Candidate") !== -1) {
            document.addEventListener('contextmenu', this.myDocument.keyupEventFunction);
            document.addEventListener('keyup', this.myDocument.printscrEventFunction(event));
        }
    }
    closeAlert(alert: any) {
        this.alertService.closeAlert(alert);
    }


}
