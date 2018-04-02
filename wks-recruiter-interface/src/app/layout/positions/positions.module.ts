import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PositionsRoutingModule } from './/positions-routing.module';
import { PositionsComponent } from './positions.component'
import { PositionsListComponent } from './positions-list/positions-list.component';
import { NgbModal, NgbModalModule } from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [
    CommonModule,
    PositionsRoutingModule
  ],
  declarations: [PositionsComponent, PositionsListComponent]
})
export class PositionsModule { }
