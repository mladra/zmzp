import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Position } from '../../../entities/position';
import { PositionsService } from '../../../services/positions.service';
import { AlertsService } from '../../../services/alerts.service';

@Component({
  selector: 'app-positions-list',
  templateUrl: './positions-list.component.html',
  styleUrls: ['./positions-list.component.scss'],
  animations: [routerTransition()]
})
export class PositionsListComponent implements OnInit {

  public positions: Position[];

  constructor(private alertsService: AlertsService, private positionService: PositionsService) { }

  ngOnInit() {
    this.positionService.getPositions()
            .subscribe(newPositions => this.positions = newPositions);
  }

  toggleActive(id: String){
    this.alertsService.addAlert('success', 'Activity of position '+id+' was succesfully changed.');
  }

  removePosition(id: String){
    this.alertsService.addAlert('success', 'Position '+id+' was succesfully removed');
  }

}
