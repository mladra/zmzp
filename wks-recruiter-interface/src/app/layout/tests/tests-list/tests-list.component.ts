import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PositionsService } from '../../../shared/services/positions.service';
import { TestsModificationComponent } from '../tests-modification/tests-modification.component';


@Component({
  selector: 'app-tests-list',
  templateUrl: './tests-list.component.html',
  styleUrls: ['./tests-list.component.scss'],
  animations: [routerTransition()]
})
export class TestsListComponent implements OnInit {

  public tests: Test[];
  private allPositions;
  private testPositions;
  private rolesToDelete;
  private rolesToAdd;

  constructor(private alertsService: AlertsService,
              private testsService: TestsService,
              private positionsService: PositionsService,
              private modalService: NgbModal
            ) {
              //this.rolesToDelete = this.allRoles && this.testRoles;
              //console.log(this.rolesToDelete);
              //this.rolesToAdd = this.allRoles.filter(element => !this.testRoles.includes(element));
              //console.log(this.rolesToAdd);
             }

  ngOnInit() {
    this.getAllTests();
    this.allPositions = this.positionsService.getAll();
  }

  addPositions(test: Test){
    this.testPositions = test.positions;
    this.rolesToAdd = this.allPositions.filter(element => !this.testPositions.includes(element));
    const modalRef = this.modalService.open(TestsModificationComponent);
    modalRef.componentInstance.name = 'Add positions to test';
    //true means we are adding roles
    modalRef.componentInstance.setTestAndPositions(test, this.rolesToAdd, true);
    modalRef.componentInstance.emitter.subscribe(
      addedPositions => {
        test.positions.concat(addedPositions);
        const index = this.tests.findIndex((t: Test) => t.id === test.id);
        this.tests[index] = test;
      }
    );
  }

  removePositions(test: Test){
    this.testPositions = test.positions;
    const modalRef = this.modalService.open(TestsModificationComponent);
    modalRef.componentInstance.name = "Remove positions from test";
    //false means we are removing roles
    modalRef.componentInstance.setTestAndPositions(test, this.testPositions, false);
    modalRef.componentInstance.emitter.subscribe(
      removedPositions => {
        test.positions = test.positions.filter(element => !removedPositions.includes(element));
        const index = this.tests.findIndex((t: Test) => t.id === test.id);
        this.tests[index] = test;
      }
    );
  }
  
  getAllTests(){
    //TODO implement when endpoint is ready
  }

}
