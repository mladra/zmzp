import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertsService } from '../../../services/alerts.service';
import { Router } from '@angular/router';
import { TestAttempt } from '../../../entities/test.attempt';

@Component({
  selector: 'app-tests-results',
  templateUrl: './tests-results.component.html',
  styleUrls: ['./tests-results.component.scss']
})
export class TestsResultsComponent implements OnInit {


  public testAttempt: TestAttempt;
  private evaluated: Boolean;

  constructor(public activeModal: NgbActiveModal,
    private alertsService: AlertsService,
    private router: Router) { }

  ngOnInit() {
  }

  close() {
    this.activeModal.close();
  }

  setTestAttempt(selectedAtempt: TestAttempt) {
    this.testAttempt = selectedAtempt;
    this.evaluated = true;
    if (this.testAttempt.score === -1) {
      this.evaluated = false;
    }
  }

}
