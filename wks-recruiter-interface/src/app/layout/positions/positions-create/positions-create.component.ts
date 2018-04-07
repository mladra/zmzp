import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbModal, NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { Position } from "../../../entities/position";
import { PositionsService } from "../../../shared/services/positions.service";
import { AlertsService } from "../../../services/alerts.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-positions-create',
  templateUrl: './positions-create.component.html',
  styles: ['./positions-create.components.scss']
})
export class PositionsCreateComponent implements OnInit {

  @Output() emitter: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  public position: Position;
  public isActive: boolean;


  constructor(public activeModal: NgbActiveModal,
              private positionsService: PositionsService,
              private alertsService: AlertsService,
              private router: Router) { }

  ngOnInit() {
    this.position = new Position();
    this.position.active = false;
  }

  close(){
    this.activeModal.close();
  }

  submit(){
    this.activeModal.close();
    this.positionsService.addPosition(this.position).subscribe(
      response => {
        this.alertsService.addAlert('success', 'New position: '+this.position.name+' added successfully.');
        this.emitter.emit(true);
      },
      error => {
        this.alertsService.addAlert('danger', error.error);
      }
    )
  }

}
