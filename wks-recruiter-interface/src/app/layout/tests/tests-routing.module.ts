import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { Routes, RouterModule } from '@angular/router';
import { TestsComponent } from "./tests.component";
import { TestsListComponent } from "./tests-list/tests-list.component";
import { TestQuestionsComponent } from './test-questions/test-questions.component';
import { TestsSolveComponent } from './tests-solve/tests-solve.component';

const routes: Routes = [
  {
    path: '',
    component: TestsComponent,
    children: [
      { path: '', redirectTo: 'list'},
      { path: 'list', component: TestsListComponent },
      { path: 'details/:id', component: TestQuestionsComponent },
      { path: 'solve/:id', component: TestsSolveComponent }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class TestsRoutingModule { }
