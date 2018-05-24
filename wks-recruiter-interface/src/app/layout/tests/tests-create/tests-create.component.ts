import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertsService } from '../../../services/alerts.service';
import { Router } from '@angular/router';
import { TestsService } from '../../../shared/services/tests.service';
import { Test } from '../../../entities/test';

@Component({
  selector: 'app-tests-create',
  templateUrl: './tests-create.component.html',
  styleUrls: ['./tests-create.component.scss']
})
export class TestsCreateComponent implements OnInit {

  @Output() emitter: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  public test: Test;
  private title: String;
  private modifying: Boolean;
  private languages;

  constructor(public activeModal: NgbActiveModal,
    private alertsService: AlertsService,
    private router: Router,
    private testsService: TestsService) { }

  ngOnInit() {
    this.languages = ["polish", "english", "spanish", "italian", "esperanto", "german", "latin", "russian"];
  }

  close() {
    this.activeModal.close();
  }

  submit() {
    this.activeModal.close();
    if (!this.modifying) {
      this.testsService.createTest(this.test).subscribe(
        response => {
          this.alertsService.addAlert('success', 'New test: ' + this.test.name + ' added successfully.');
          this.emitter.emit(true);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
    } else {
      this.testsService.editTest(this.test).subscribe(
        response => {
          this.alertsService.addAlert('success', 'Test: ' + this.test.name + ' successfully modified.');
          this.emitter.emit(true);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
    }
    this.modifying = true;
  }

  setTest(testToEdit: Test, modifying: Boolean) {
    if (modifying) {
      this.test = testToEdit;
      this.modifying = modifying;
      this.title = 'Modify test information.';
    } else {
      this.title = 'Create new test.';
      this.modifying = modifying;
      this.test = new Test();
    }
  }

}
