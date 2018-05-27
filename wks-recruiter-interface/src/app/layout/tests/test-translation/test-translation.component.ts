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
  private currentLanguage: String;
  private tests: Array<Test>;
  private languages;

  constructor(public activeModal: NgbActiveModal,
    private alertsService: AlertsService) {
  }

  ngOnInit() {
    this.languages = ["polish", "english", "spanish", "italian", "esperanto", "german", "latin", "russian"];
    this.deleteCurrentLanguage(this.languages);
  }

  setTests(tests: Array<Test>) {
    this.tests = tests;
  }

  setTestName(name: string) {
    this.name = name;
  }

  setCurrentLanguage(language: String) {
      this.currentLanguage = language;
  }

  deleteCurrentLanguage(languages) {
    let idx = languages.findIndex(x => x === this.currentLanguage);
    if (idx > -1) {
      this.languages.splice(idx, 1);
    }
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
