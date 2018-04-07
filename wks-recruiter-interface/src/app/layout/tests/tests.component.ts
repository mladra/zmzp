import { Component, OnInit } from '@angular/core';
import { routerTransition } from "../../router.animations";

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.scss'],
  animations: [routerTransition()]
})
export class TestsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
