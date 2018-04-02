import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { Position } from "../../../entities/position";
import { PositionsService } from "../../../services/positions.service";
import { AlertsService } from "../../../services/alerts.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-positions-create',
  templateUrl: './positions-create.component.html'
})
export class PositionsCreateComponent implements OnInit {

  public position: Position;
  public isActive: boolean;


  constructor(public activeModal: NgbActiveModal,
              private positionsService: PositionsService,
              private alertsService: AlertsService,
              private router: Router) { }

  ngOnInit() {
  }

  close(){
    this.activeModal.close();
  }

  submit(){
    this.activeModal.close();
    this.positionsService.addPosition(this.position).subscribe(
      response => {
        this.alertsService.addAlert('success', 'New position: '+this.position.name+' added successfully.');
      },
      error => {
        this.alertsService.addAlert('danger', error.error);
      }
    )
  }

}
