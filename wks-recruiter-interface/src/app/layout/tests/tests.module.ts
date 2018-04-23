import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { TestsComponent } from "./tests.component";
import { TestsRoutingModule } from './tests-routing.module';
import { TestsListComponent } from './tests-list/tests-list.component';
import { TestsModificationComponent } from './tests-modification/tests-modification.component';
import { FormsModule } from '@angular/forms';
import { TestQuestionsComponent } from './test-questions/test-questions.component';
import { TestsCreateComponent } from './tests-create/tests-create.component';

@NgModule({
  imports: [
    CommonModule,
    TestsRoutingModule,
    CommonModule,
    FormsModule,
    NgbModule.forRoot()
  ],
  declarations: [TestsComponent, TestsListComponent, TestsModificationComponent, TestQuestionsComponent, TestsCreateComponent],
  entryComponents: [TestsModificationComponent, TestsCreateComponent]
})
export class TestsModule { }
