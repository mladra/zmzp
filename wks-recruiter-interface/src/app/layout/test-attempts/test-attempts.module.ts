import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TestAttemptsRoutingModule } from './test-attempts-routing.module';
import { TestAttemptsComponent } from './test-attempts.component';
import { TestAttemptsListComponent } from './test-attempts-list/test-attempts-list.component';
import { TestAttemptsAnswersComponent } from './test-attempts-answers/test-attempts-answers.component';

@NgModule({
  imports: [
    CommonModule,
    TestAttemptsRoutingModule,
    FormsModule,
    NgbModule.forRoot()
  ],
  declarations: [
    TestAttemptsComponent,
    TestAttemptsListComponent,
    TestAttemptsAnswersComponent
  ]
})
export class TestAttemptsModule { }
