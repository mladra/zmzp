import { Component, OnInit } from "@angular/core";
import { routerTransition } from "../../../router.animations";
import { TestsService } from "../../../shared/services/tests.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Test } from "../../../entities/test";
import { AlertsService } from "../../../services/alerts.service";

@Component({
    selector: 'app-tests-solve',
    templateUrl: './tests-solve.component.html',
    styleUrls: ['./tests-solve.component.scss'],
    animations: [routerTransition()]
})
export class TestsSolveComponent implements OnInit {

    private test: Test;

    constructor(
        private testsService: TestsService,
        private route: ActivatedRoute,
        private router: Router,
        private alertsService: AlertsService
    ) { 
        this.test = new Test();
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.testsService.getById(params['id']).subscribe(
                data => {
                    this.test = data.body as Test;
                    console.log(this.test);
                }, 
                error => {
                    this.alertsService.addAlert('danger', "Couldn't retrieve test from server.");
                }
            )
        })
    }
}