import { Component, OnInit, Output } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PositionsService } from '../../../shared/services/positions.service';
import { TestsModificationComponent } from '../tests-modification/tests-modification.component';
import { Position } from '../../../entities/position';
import { Router, ActivatedRoute } from '@angular/router';
import { QuestionTypeEnum } from '../../../entities/question.type.enum';
import { QuestionInfo } from '../../../entities/question.info';
import { QuestionsService } from '../../../shared/services/questions.service';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl } from '@angular/forms';


@Component({
  selector: 'app-test-questions',
  templateUrl: './test-questions.component.html',
  styleUrls: ['./test-questions.component.scss'],
  animations: [routerTransition()]
})
export class TestQuestionsComponent implements OnInit {

  private test: Test;
  private fromLanguage: string;
  private toLanguage: string;
  private lang;
  private originalQuestions: Array<QuestionInfo>;
  private questionTypes: [string, any][];
  private numberForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestsService,
    private alertsService: AlertsService,
    private questionsService: QuestionsService
  ) {
    this.test = new Test();
    this.questionTypes = Object.entries(QuestionTypeEnum);
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.testService.getById(params['id']).subscribe(
        data => {
          this.test = data.body as Test;
          this.fromLanguage = this.test.language;
          this.route.queryParams.subscribe(queryParams => {
            if (queryParams.translate) {
              this.toLanguage = queryParams.language;
              this.originalQuestions = JSON.parse(JSON.stringify(this.test.questions));
              this.test.questions.forEach(question => {
                question.questionPhrase = null;
                if (question.type === 'SINGLE_CHOICE' || question.type === 'MULTIPLE_CHOICE') {
                  question.params.options = new Array(question.params.options.length);
                }
              });
              this.setLanguages(this.fromLanguage, this.toLanguage);
            }
          });
        },
        error => {
          this.alertsService.addAlert('danger', "Couldn't retrieve test questions from server.");
        }
      );
    });
  }

  setLanguages(fromLanguage, toLanguage) {
    var fromLang;
    var toLang;
    if (fromLanguage == 'polish') {
      fromLang = 'pl';
    } else if (fromLanguage == 'english') {
      fromLang = 'en';
    } else if (fromLanguage == 'russian') {
      fromLang = 'ru';
    } else if (fromLanguage == 'german') {
      fromLang = 'de';
    } else if (fromLanguage == 'italian') {
      fromLang = 'it';
    } else if (fromLanguage == 'spanish') {
      fromLang = 'es';
    }

    if (toLanguage == 'polish') {
      toLang = 'pl';
    } else if (toLanguage == 'english') {
      toLang = 'en';
    } else if (toLanguage == 'russian') {
       toLang = 'ru';
    } else if (toLanguage == 'german') {
      toLang = 'de';
    } else if (toLanguage == 'italian') {
      toLang = 'it';
    } else if (toLanguage == 'spanish') {
      toLang = 'es';
    }
    this.lang = fromLang + '-' + toLang;
  }

  addQuestion() {
    this.test.questions.push(new QuestionInfo());
  }

  removeQuestion(question) {
    const index = this.test.questions.indexOf(question, 0);
    if (index > -1) {
      this.test.questions.splice(index, 1);
    }
  }

  translateQuestion(question, targetQuestion) {
    var myWindow: any = window;
    myWindow.$.ajax({
      url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
      data: {
        key: 'trnsl.1.1.20180517T202518Z.2b1415d40fcc9baf.1d23d85fc47ccdbb8a4ba43da6e73174cb54bdc6\n',
        text: question.questionPhrase,
        lang: this.lang,
        format: 'plain',
        options: 1,
      },
      dataType: 'jsonp',
      success: function (x) {
        if (x.text.length > 0) {
          targetQuestion.questionPhrase = x.text[0];
          } else {
            alert("No translation found");
          }
        }
    });
  }

  translateOption(option, targetQuestions, jthQuestion, ithOption) {
    var myWindow: any = window;
    myWindow.$.ajax({
      url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
      data: {
        key: 'trnsl.1.1.20180517T202518Z.2b1415d40fcc9baf.1d23d85fc47ccdbb8a4ba43da6e73174cb54bdc6\n',
        text: option,
        lang: this.lang,
        format: 'plain',
        options: 1,
        },
        dataType: 'jsonp',
        success: function (x) {
          if (x.text.length > 0) {
            targetQuestions[jthQuestion].params.options[ithOption] = x.text[0];
          } else {
            alert("No translation found");
          }
        }
      });
    }

  addQuestionOption(question) {
    question.params.options.push('');
  }

  removeOptionFromQuestion(question, i) {
    question.params.options.splice(i, 1);
  }

  questionTypeChanged(question, value) {
    const index = this.test.questions.indexOf(question);
    if (index === -1) {
      console.log('Question not found!');
      return;
    }

    const parsedValue = QuestionTypeEnum[value];
    switch (parsedValue) {
      case QuestionTypeEnum.STRING:
        question.params = null;
        break;
      case QuestionTypeEnum.SINGLE_CHOICE:
      case QuestionTypeEnum.MULTIPLE_CHOICE:
        this.test.questions[index].params = {
          options: Array<String>()
        };
        this.test.questions[index].params.options.push('');
        break;
      case QuestionTypeEnum.NUMBER:
        this.test.questions[index].params = {
          minValue: Number,
          maxValue: Number
        };
        break;
      case QuestionTypeEnum.SCALE:
        this.test.questions[index].params = {
          minValue: Number,
          maxValue: Number,
          step: Number
        };
        break;
    }
  }

  trackByFun(index, question) {
    return index;
  }

  submit() {
    if (this.originalQuestions !== undefined) {
      this.test.id = null;
      this.test.language = this.toLanguage;
      this.testService.createTest(this.test).subscribe(
        response => {
          this.alertsService.addAlert('success', 'Test "' + this.test.name + '"successfully translated.');
          this.router.navigate(['tests/list']);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
    } else {

      for (const question of this.test.questions) {
        if (question.type === 'NONE' || question.maxPoints <= 0) {
          return;
        }

        if (question.type === 'NUMBER' || question.type === 'SCALE') {
          if (question.params.maxValue < question.params.minValue) {
            return;
          }
        }

        if (question.type === 'SCALE') {
          if (question.params.step <= 0 || (question.params.step > question.params.maxValue - question.params.minValue &&
            question.params.maxValue > question.params.minValue)) {
            return;
          }
        }
      }

      this.questionsService.setQuestions(this.test.id, this.test.questions).subscribe(
        data => {
          this.alertsService.addAlert('success', 'Questions successfully updated.');
          this.router.navigate(['/tests/list']);
        },
        error => {
          console.log(error);
          this.alertsService.addAlert('danger', 'Error occurred during questions update. Try again...');
        }
      );
    }
  }
}
