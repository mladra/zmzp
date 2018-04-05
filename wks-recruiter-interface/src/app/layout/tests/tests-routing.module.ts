import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { Routes, RouterModule } from '@angular/router';
import { TestsComponent } from "./tests.component";
import { TestsListComponent } from "./tests-list/tests-list.component";

const routes: Routes = [
  {
    path: '',
    component: TestsComponent,
    children: [
      { path: '', redirectTo: 'list'},
      { path: 'list', component: TestsListComponent }
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
