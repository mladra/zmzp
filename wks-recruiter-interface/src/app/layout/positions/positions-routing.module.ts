import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { PositionsComponent } from './positions.component';
import { PositionsListComponent } from './positions-list/positions-list.component';

const routes: Routes = [
  {
    path: '',
    component: PositionsComponent,
    children: [
      { path: '', redirectTo: 'list' },
      { path: 'list', component: PositionsListComponent }
    ]
  }
]

@NgModule({
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class PositionsRoutingModule { }
