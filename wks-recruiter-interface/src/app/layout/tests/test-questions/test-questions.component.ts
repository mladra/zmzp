import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PositionsService } from '../../../shared/services/positions.service';
import { TestsModificationComponent } from '../tests-modification/tests-modification.component';
import { Position } from '../../../entities/position';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-test-questions',
  templateUrl: './test-questions.component.html',
  styleUrls: ['./test-questions.component.scss'],
  animations: [routerTransition()]
})
export class TestQuestionsComponent implements OnInit {

  private test: Test;

  constructor (
    private route: ActivatedRoute,
    private testService: TestsService,
    private alertsService: AlertsService
  ) {
    this.test = new Test();
  }

  ngOnInit() { 
    this.route.params.subscribe(params => {
      this.testService.getById(params['id']).subscribe(
        data => {
          this.test = data.body as Test;
          console.log(this.test);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
   });
  }
}
