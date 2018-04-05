import { Component, OnInit, TemplateRef } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Position } from '../../../entities/position';
import { PositionsService } from '../../../shared/services/positions.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PositionsCreateComponent } from "../positions-create/positions-create.component";

@Component({
  selector: 'app-positions-list',
  templateUrl: './positions-list.component.html',
  styleUrls: ['./positions-list.component.scss'],
  animations: [routerTransition()]
})
export class PositionsListComponent implements OnInit {

  public positions: Position[];

  constructor(private alertsService: AlertsService,
              private positionService: PositionsService,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.getAllPositions();
  }

  addPosition(){
    const modalRef = this.modalService.open(PositionsCreateComponent);
    modalRef.componentInstance.name = 'Create position';
    modalRef.componentInstance.emitter.subscribe(
      emittedBoolean => {
        this.getAllPositions();
      }
    )
    // modalRef.result.then(
    //   d => {
    //     console.log(d);
    //     console.log("outside modal");
    //     this.getAllPositions();
    //   },
    //   error => {
    //     console.log(error);
    //   }
    // );
  }

  modifyPosition(position: Position){
    if(position.active){
      position.active = false;
    } else {
      position.active = true;
    }
    this.positionService.modifyPosition(position.name, position.active).subscribe(
      response => {
        this.alertsService.addAlert('success', 'Successfully toggled activity of '+position.name+' position');
      },
      error => {
        this.alertsService.addAlert('darger', error.error);
      });
  }

  getAllPositions(){
    this.positionService.getAll().subscribe(
      data => {
        const positionString = JSON.stringify(data.body);
        this.positions = JSON.parse(positionString);
      },
      error => {
        console.log(error);
        this.alertsService.addAlert('danger', 'Error occured while loading positions');
      });
  }

}
