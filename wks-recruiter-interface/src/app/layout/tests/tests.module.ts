import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestsComponent } from "./tests.component";
import { TestsRoutingModule } from './tests-routing.module';
import { TestsListComponent } from './tests-list/tests-list.component';
import { TestsModificationComponent } from './tests-modification/tests-modification.component';
import { FormsModule } from '@angular/forms';
import { TestQuestionsComponent } from './test-questions/test-questions.component';

@NgModule({
  imports: [
    CommonModule,
    TestsRoutingModule,
    CommonModule,
    FormsModule
  ],
  declarations: [TestsComponent, TestsListComponent, TestsModificationComponent, TestQuestionsComponent],
  entryComponents: [TestsModificationComponent]
})
export class TestsModule { }
