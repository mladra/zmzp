import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestsRoutingModule } from './tests-routing.module';
import { TestsListComponent } from './tests-list/tests-list.component';

@NgModule({
  imports: [
    CommonModule,
    TestsRoutingModule
  ],
  declarations: [TestsListComponent]
})
export class TestsModule { }
