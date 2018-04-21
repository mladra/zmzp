import { Component, OnInit } from "@angular/core";
import { routerTransition } from "../../../router.animations";
import { TestsService } from "../../../shared/services/tests.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Test } from "../../../entities/test";
import { AlertsService } from "../../../services/alerts.service";
import { TestAttempt } from "../../../entities/test.attempt";
import { AttemptAnswer } from "../../../entities/attempt.answer";

@Component({
    selector: 'app-tests-solve',
    templateUrl: './tests-solve.component.html',
    styleUrls: ['./tests-solve.component.scss'],
    animations: [routerTransition()]
})
export class TestsSolveComponent implements OnInit {

    private test: Test;

    private testAttempt: TestAttempt;

    constructor(
        private testsService: TestsService,
        private route: ActivatedRoute,
        private router: Router,
        private alertsService: AlertsService
    ) {
        this.test = new Test();
        this.testAttempt = new TestAttempt();
        this.testAttempt.answers = new Array<AttemptAnswer>();
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.testsService.getById(params['id']).subscribe(
                data => {
                    this.test = data.body as Test;
                },
                error => {
                    this.alertsService.addAlert('danger', "Couldn't retrieve test from server.");
                }
            )
        })
    }
}