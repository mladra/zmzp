import { Component, OnInit } from "@angular/core";
import { routerTransition } from "../../../router.animations";
import { TestsService } from "../../../shared/services/tests.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Test } from "../../../entities/test";
import { AlertsService } from "../../../services/alerts.service";
import { TestAttempt } from "../../../entities/test.attempt";
import { AttemptAnswer } from "../../../entities/attempt.answer";
import { CurrentUserService } from "../../../services/current-user.service";
import { Account } from '../../../entities/account';

@Component({
    selector: 'app-tests-solve',
    templateUrl: './tests-solve.component.html',
    styleUrls: ['./tests-solve.component.scss'],
    animations: [routerTransition()]
})
export class TestsSolveComponent implements OnInit {

    private test: Test;

    private testAttempt: TestAttempt;

    private currentAccount: Account;

    constructor(
        private testsService: TestsService,
        private route: ActivatedRoute,
        private router: Router,
        private alertsService: AlertsService,
        private currentUserService: CurrentUserService
    ) {
        this.test = new Test();
        this.testAttempt = new TestAttempt();
        this.testAttempt.answers = new Array<AttemptAnswer>();
    }

    ngOnInit(): void {
        this.currentUserService.getCurrentUser().subscribe(
            data => {
                this.currentAccount = data;
            },
            error => {
                this.alertsService.addAlert('danger', 'Error occurred while loading account.');
            }
        )
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

    trackByFun(index, question) {
        return index;
    }

    submit() {
        this.testAttempt.test = this.test;
        for (let i in this.test.questions) {
            if (this.test.questions[i].type === 'NUMBER' || this.test.questions[i].type === 'SCALE') {
                if (this.test.questions[i].answer < this.test.questions[i].params.minValue
                    || this.test.questions[i].answer > this.test.questions[i].params.maxValue) {
                    this.alertsService.addAlert('danger', 'Enter proper values!');
                    this.testAttempt.answers = new Array<AttemptAnswer>();
                    return;
                }
            }
            var attemptAnswer = new AttemptAnswer();
            attemptAnswer.question = this.test.questions[i].questionPhrase;
            attemptAnswer.questionNumber = this.test.questions[i].questionNumber;
            if (this.test.questions[i].type === 'MULTIPLE_CHOICE') {
                attemptAnswer.answers = this.test.questions[i].answer;
                console.log(this.test.questions[i]);
            } else {
                var answers = new Array<String>();
                answers.push(this.test.questions[i].answer);
                attemptAnswer.answers = answers;
            }
            this.testAttempt.answers.push(attemptAnswer);
        }
        this.testsService.solveTest(this.testAttempt).subscribe(
            data => {
                this.alertsService.addAlert('success', 'Thank you for solving this test!');
                this.currentAccount.solvedTests.push(this.testAttempt);
                this.currentUserService.setCurrentUser(this.currentAccount);
                this.testAttempt.answers = new Array<AttemptAnswer>();
                this.router.navigate(['/tests/list']);
            },
            error => {
                this.testAttempt.answers = new Array<AttemptAnswer>();
                if (error.error === 'Test already solved.\n') {
                    this.alertsService.addAlert('danger', error.error);
                } else {
                    this.alertsService.addAlert('danger', 'Error occured during sending your test');
                }
            }
        )
    }
}