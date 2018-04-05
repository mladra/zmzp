import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.scss'],
  animations: [routerTransition()]
})
export class PositionsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
