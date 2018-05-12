import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { TestAttempt } from '../../../entities/test.attempt';
import { ActivatedRoute, Router } from '@angular/router';
import { StorageService } from '../../../shared/services/storage.service';
import { TestAttemptsService } from '../../../shared/services/tests.attempts.serice';
import { AlertsService } from '../../../services/alerts.service';

@Component({
    selector: 'app-test-attempts-answers',
    templateUrl: './test-attempts-answers.component.html',
    styleUrls: ['./test-attempts-answers.component.scss'],
    animations: [routerTransition()]
})
export class TestAttemptsAnswersComponent implements OnInit {

    private sendNotification: boolean;
    private evaluating: boolean;
    private testAttempt: TestAttempt;

    constructor(
        private route: ActivatedRoute,
        private storageService: StorageService,
        private router: Router,
        private testAttemptsService: TestAttemptsService,
        private alertsService: AlertsService
    ) {
    }

    ngOnInit() {
        this.sendNotification = false;
        this.evaluating = false;
        this.testAttempt = JSON.parse(this.storageService.getDataFromStorage());
        for (const answer of this.testAttempt.answers) {
            if (answer.score === -1) {
                answer.score = null;
            }
        }
    }

    trackByFun(index, question) {
        return index;
    }

    submit() {
        this.evaluating = true;
        for (const answer of this.testAttempt.answers) {
            if (answer.score < 0 || answer.score > answer.maxPoints) {
                this.evaluating = false;
                return;
            }
        }

        this.testAttemptsService.updateTestAttempt(this.testAttempt.user, this.testAttempt).subscribe(
            response => {
                this.alertsService.addAlert('success', 'Test attempt successfully evaluated.');
                if (this.sendNotification) {
                    this.testAttemptsService.sendEmailNotification(this.testAttempt.user, response.body).subscribe(
                        res => {
                            this.evaluating = false;
                            this.alertsService.addAlert('success', 'Notification has been successfully sent to ' + this.testAttempt.user);
                            this.storageService.clearStorage();
                            this.router.navigate(['/test-attempts/list']);
                        },
                        err => {
                            this.evaluating = false;
                            this.alertsService.addAlert('danger', 'Error occurred during notification sending process. Please try again.');
                        }
                    );
                } else {
                    this.evaluating = false;
                    this.storageService.clearStorage();
                    this.router.navigate(['/test-attempts/list']);
                }
            },
            error => {
                this.evaluating = false;
                this.alertsService.addAlert('danger', 'Error occurred during evaluating test attempt. Please try again.');
            }
        );
    }
}
