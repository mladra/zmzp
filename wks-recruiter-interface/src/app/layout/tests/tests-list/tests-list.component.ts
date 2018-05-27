import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PositionsService } from '../../../shared/services/positions.service';
import { TestsModificationComponent } from '../tests-modification/tests-modification.component';
import { Position } from '../../../entities/position';
import { Router, ActivatedRoute } from '@angular/router';
import { CurrentUserService } from '../../../services/current-user.service';
import { Observable } from 'rxjs/Observable';
import { TestsCreateComponent } from '../tests-create/tests-create.component';
import { Account } from '../../../entities/account';
import { TestTranslationComponent } from '../test-translation/test-translation.component';
import { TestAttempt } from '../../../entities/test.attempt';
import { TestAttemptsService } from "../../../shared/services/tests.attempts.serice";
import { TestsResultsComponent } from '../tests-results/tests-results.component';

@Component({
  selector: 'app-tests-list',
  templateUrl: './tests-list.component.html',
  styleUrls: ['./tests-list.component.scss'],
  animations: [routerTransition()]
})
export class TestsListComponent implements OnInit {

  public tests: Array<Test> = new Array<Test>();
  private allPositions: Array<Position>;
  private allPositionNames: Array<String>;
  private testPositionNames: Array<String>;
  private positionsToAdd: Array<String>;
  private currentAccount: Account;
  private testAttempts: Array<TestAttempt> = new Array<TestAttempt>();

  constructor(private alertsService: AlertsService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private testsService: TestsService,
    private testsAttemptService: TestAttemptsService,
    private positionsService: PositionsService,
    private currentUserService: CurrentUserService
  ) { }

  ngOnInit() {
    this.currentUserService.getCurrentUser().subscribe(
      data => {
        this.currentAccount = data;
      },
      error => {
        this.alertsService.addAlert('danger', 'Error occurred while loading account.');
      }
    );
    this.positionsService.getAll().subscribe(
      data => {
        const positionString = JSON.stringify(data.body);
        this.allPositions = JSON.parse(positionString);
        const that = this;
        this.allPositionNames = [];
        this.allPositions.forEach(x => that.allPositionNames.push(x.name));
      },
      error => {
        console.log(error);
        this.alertsService.addAlert('danger', 'Error occured while loading positions');
      });
    this.getAllTests();
    this.getAllTestAttempts();
  }

  addTest() {
    const modalRef = this.modalService.open(TestsCreateComponent);
    modalRef.componentInstance.name = 'Create test';
    modalRef.componentInstance.setTest(null, false);
    modalRef.componentInstance.emitter.subscribe(
      emittedBoolean => {
        this.getAllTests();
      }
    );
  }

  editTest(test: Test) {
    const modalRef = this.modalService.open(TestsCreateComponent);
    modalRef.componentInstance.name = 'Edit test';
    const testCopy = new Test();
    testCopy.id = test.id;
    testCopy.name = test.name;
    testCopy.language = test.language;
    testCopy.description = test.description;
    modalRef.componentInstance.setTest(testCopy, true);
    modalRef.componentInstance.emitter.subscribe(
      emittedBoolean => {
        this.getAllTests();
      }
    );
  }

  addPositions(test: Test) {
    if (test.active === true) {
      const that = this;
      that.testPositionNames = [];
      if (test.positions != null) {
        test.positions.forEach(x => { that.testPositionNames.push(x.name); });
        this.positionsToAdd = this.allPositionNames.filter(element => !this.testPositionNames.includes(element));
      } else {
        this.positionsToAdd = this.allPositionNames;
        test.positions = new Array<Position>();
      }
      console.log(this.positionsToAdd);
      const modalRef = this.modalService.open(TestsModificationComponent);
      modalRef.componentInstance.name = 'Add positions to test';
      // true means we are adding roles
      modalRef.componentInstance.setTestAndPositions(test, this.positionsToAdd, true);
      modalRef.componentInstance.emitter.subscribe(
        refresh => {
          this.getAllTests();
        }
      );
    }
  }

  removePositions(test: Test) {
    if (test.active === true) {
      const that = this;
      that.testPositionNames = [];
      test.positions.forEach(x => { that.testPositionNames.push(x.name); });
      const modalRef = this.modalService.open(TestsModificationComponent);
      modalRef.componentInstance.name = 'Remove positions from tes';
      // false means we are removing roles
      modalRef.componentInstance.setTestAndPositions(test, this.testPositionNames, false);
      modalRef.componentInstance.emitter.subscribe(
        refresh => {
          this.getAllTests();
        }
      );
    }
  }

  deleteTest(test: Test) {
    if (test.active === true) {
      this.testsService.deleteTest(test.id).subscribe(
        response => {
          this.alertsService.addAlert('success', 'Successfully removed ' + test.name + ' test');
          this.getAllTests();
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        });
    }
  }

  getAllTests() {
    if (this.isCurrentUserEditor()) {
      this.testsService.getEditorTests().subscribe(
        data => {
          this.tests = data as Array<Test>;
        },
        error => {
          this.alertsService.addAlert('danger', 'Error occurred while loading tests.');
        });
    } else if (this.isCurrentUserCandidate()) {
      this.testsService.getCandidateTests().subscribe(
        data => {
          this.tests = data as Array<Test>;
        },
        error => {
          this.alertsService.addAlert('danger', 'Error occurred while loading tests.');
        });
    }
  }

  getAllTestAttempts(){
    if(this.isCurrentUserCandidate()) {
      this.testsAttemptService.getTestsAttemptsForUser().subscribe(
        data => {
          this.testAttempts = data.body as Array<TestAttempt>;
        },
        error => {
          this.alertsService.addAlert('danger', 'Error occured while getting test attempts');
        }
      )
    }
  }

  goToQuestions(id) {
    this.router.navigate(['tests/details', id]);
  }

  goToTestSolving(id) {
    this.router.navigate(['tests/solve', id]);
  }

  pdf(test: Test) { this.testsService.getPDF(test.id, test.name); }
  xls(test: Test) { this.testsService.getXLS(test.id, test.name); }

  isCurrentUserEditor() {
    return this.currentUserService.isCurrentUserInRole('Editor');
  }

  isCurrentUserCandidate() {
    return this.currentUserService.isCurrentUserInRole('Candidate');
  }

  isTestSolved(test: Test) {
    for (const testIteration of this.currentAccount.solvedTests) {
      if (testIteration.test.id === test.id) {
        return true;
      }
    }
    return false;
  }

  translateTest(id, name) {
    const modalRef = this.modalService.open(TestTranslationComponent);
    modalRef.componentInstance.setTests(this.tests, true);
    modalRef.componentInstance.setTestName(name, true);
    let test = this.tests.find(test => test.id === id && test.name === name);
    if (test !== undefined) {
      modalRef.componentInstance.setCurrentLanguage(test.language);
    }
    modalRef.componentInstance.emitter.subscribe(
      emittedBoolean => {
        const language = modalRef.componentInstance.close();
        this.router.navigate(['tests/details', id], { queryParams: { translate: true, language: language } });
      }
    );
  }

  showResults(testAttempt: TestAttempt) {
    const modalRef = this.modalService.open(TestsResultsComponent, { size: 'lg'});
    modalRef.componentInstance.name = 'Test results';
    modalRef.componentInstance.setTestAttempt(testAttempt);
  }
}
