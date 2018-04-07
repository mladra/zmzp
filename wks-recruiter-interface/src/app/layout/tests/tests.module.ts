import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestsComponent } from "./tests.component";
import { TestsRoutingModule } from './tests-routing.module';
import { TestsListComponent } from './tests-list/tests-list.component';
import { TestsModificationComponent } from './tests-modification/tests-modification.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TestsRoutingModule,
    CommonModule,
    FormsModule
  ],
  declarations: [TestsComponent, TestsListComponent, TestsModificationComponent],
  entryComponents: [TestsModificationComponent]
})
export class TestsModule { }
