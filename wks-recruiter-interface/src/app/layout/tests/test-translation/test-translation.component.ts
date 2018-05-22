import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Test } from '../../../entities/test';
import { TestsListComponent } from '../tests-list/tests-list.component';
import { AlertsService } from '../../../services/alerts.service';

@Component({
  selector: 'app-test-translation',
  templateUrl: './test-translation.component.html',
  styleUrls: ['./test-translation.component.scss']
})
export class TestTranslationComponent implements OnInit {

  @Output() emitter: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  private name: string;
  private language: String;
  private tests: Array<Test>;
  private languages;

  constructor(public activeModal: NgbActiveModal,
    private alertsService: AlertsService) { }

  ngOnInit() {
    this.languages = [
      {id: "polish", name: "Polish"},
      {id: "english", name: "English"},
      {id: "spanish", name: "Spanish"},
      {id: "italian", name: "Italian"},
      {id: "esperanto", name: "Esperanto"},
      {id: "german", name: "German"},
      {id: "latin", name: "Latin"},
      {id: "russian", name: "Russian"}
    ];
  }

  setTests(tests: Array<Test>) {
    this.tests = tests;
  }

  setTestName(name: string) {
    this.name = name;
  }

  close() {
    this.activeModal.close();
    return this.language;
  }

  submit() {
    if (this.tests.find(test => test.language === this.language && test.name === this.name) !== undefined) {
      this.alertsService.addAlert('danger', 'Test with such name and language already exists.');
      this.close();
    } else {
      this.emitter.emit(true);
    }
  }
}
