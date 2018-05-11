import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { AlertsService } from '../../../services/alerts.service';
import { Router, NavigationExtras } from '@angular/router';
import { TestAttemptsService } from '../../../shared/services/tests.attempts.serice';
import { TestAttempt } from '../../../entities/test.attempt';
import { StorageService } from '../../../shared/services/storage.service';


@Component({
  selector: 'app-test-attempts-list',
  templateUrl: './test-attempts-list.component.html',
  styleUrls: ['./test-attempts-list.component.scss'],
  animations: [routerTransition()]
})
export class TestAttemptsListComponent implements OnInit {

  private testAttempts: Array<TestAttempt> = new Array<TestAttempt>();
  private retrievedMap: Map<string, TestAttempt[]>;

  constructor(
    private testAttemptsService: TestAttemptsService,
    private router: Router,
    private alertsService: AlertsService,
    private storageService: StorageService
  ) { }

  ngOnInit() {
    this.testAttemptsService.getTestsAttempts().subscribe(
      response => {
        this.retrievedMap = response.body as Map<string, TestAttempt[]>;
        for (const key of Object.keys(this.retrievedMap)) {
          for (const value of this.retrievedMap[key]) {
            const testAttempt: TestAttempt = new TestAttempt();
            testAttempt.user = key;
            testAttempt.test = value.test;
            testAttempt.score = value.score;
            testAttempt.maxPoints = value.maxPoints;
            testAttempt.answers = value.answers;
            this.testAttempts.push(testAttempt);
          }
        }
      },
      error => {
        console.log(error);
        this.alertsService.addAlert('danger', 'Couldn\'t retrieve test attempts from server.');
      }
    );
  }

  checkTestAttempt(attempt) {
    this.storageService.addDataToStorage(JSON.stringify(attempt));
    this.router.navigate(['test-attempts/answers']);
  }
}
