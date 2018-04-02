import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PositionsRoutingModule } from './/positions-routing.module';
import { PositionsComponent } from './positions.component'
import { PositionsListComponent } from './positions-list/positions-list.component';
import { NgbModal, NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { PositionsCreateComponent } from "./positions-create/positions-create.component";
import { FormsModule, NgForm } from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    PositionsRoutingModule
  ],
  declarations: [PositionsComponent, PositionsListComponent, PositionsCreateComponent],
  entryComponents: [PositionsCreateComponent]
})
export class PositionsModule { }
