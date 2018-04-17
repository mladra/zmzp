import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PositionsService } from '../../../shared/services/positions.service';
import { TestsModificationComponent } from '../tests-modification/tests-modification.component';
import { Position } from '../../../entities/position';
import { Router } from '@angular/router';
import { CurrentUserService } from '../../../services/current-user.service';
import { Observable } from 'rxjs/Observable';


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
  // private currentUserRoles: Array<String>;

  constructor(private alertsService: AlertsService,
    private testsService: TestsService,
    private positionsService: PositionsService,
    private modalService: NgbModal,
    private router: Router,
    private currentUserService: CurrentUserService
  ) { }

  ngOnInit() {
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
  }

  addPositions(test: Test) {
    if (test.active === true) {
      const that = this;
      that.testPositionNames = [];
      test.positions.forEach(x => { that.testPositionNames.push(x.name); });
      this.positionsToAdd = this.allPositionNames.filter(element => !this.testPositionNames.includes(element));
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

  goToQuestions(id) {
    this.router.navigate(['tests/details', id]);
  }

  pdf(test: Test) { this.testsService.getPDF(test.id, test.name); }
  xls(test: Test) { this.testsService.getXLS(test.id, test.name); }

  isCurrentUserEditor() {
    return this.currentUserService.isCurrentUserInRole('Editor');
  }

  isCurrentUserCandidate() {
    return this.currentUserService.isCurrentUserInRole('Candidate');
  }
}
