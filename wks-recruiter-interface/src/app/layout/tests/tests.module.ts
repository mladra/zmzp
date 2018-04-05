import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestsComponent } from "./tests.component";
import { TestsRoutingModule } from './tests-routing.module';
import { TestsListComponent } from './tests-list/tests-list.component';

@NgModule({
  imports: [
    CommonModule,
    TestsRoutingModule,
    CommonModule
  ],
  declarations: [TestsComponent, TestsListComponent]
})
export class TestsModule { }
