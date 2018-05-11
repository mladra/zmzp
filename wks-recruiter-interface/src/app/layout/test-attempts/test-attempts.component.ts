import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-tests',
  templateUrl: './test-attempts.component.html',
  styleUrls: ['./test-attempts.component.scss'],
  animations: [routerTransition()]
})
export class TestAttemptsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
