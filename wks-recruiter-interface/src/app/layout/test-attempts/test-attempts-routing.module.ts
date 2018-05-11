import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { TestAttemptsComponent } from './test-attempts.component';
import { TestAttemptsListComponent } from './test-attempts-list/test-attempts-list.component';
import { TestAttemptsAnswersComponent } from './test-attempts-answers/test-attempts-answers.component';

const routes: Routes = [
  {
    path: '',
    component: TestAttemptsComponent,
    children: [
      { path: '', redirectTo: 'list'},
      { path: 'list', component: TestAttemptsListComponent },
      { path: 'answers', component: TestAttemptsAnswersComponent }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})

export class TestAttemptsRoutingModule { }
