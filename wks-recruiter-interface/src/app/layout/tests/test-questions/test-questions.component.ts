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
        },
        error => {
          this.alertsService.addAlert('danger', "Couldn't retrieve test questions from server.");
        }
      );
    });
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
