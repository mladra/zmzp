import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Test } from '../../../entities/test';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tests-modification',
  templateUrl: './tests-modification.component.html',
  styleUrls: ['./tests-modification.component.scss']
})
export class TestsModificationComponent implements OnInit {

  @Output() emitter: EventEmitter<String[]> = new EventEmitter<String[]>();

  public test: Test;
  public potentialPositions: String[];
  public selectedPositions: String[];
  public addingPositions: boolean;
  private title: string

  constructor(public activeModal: NgbActiveModal,
              private testsService: TestsService,
              private alertsService: AlertsService,
              private router: Router) { }

  ngOnInit() {

  }

  close(){
    this.activeModal.close();
  }

  submit(){
    this.activeModal.close();
    if(this.addingPositions){
      this.testsService.addPositions(this.test.id, this.selectedPositions).subscribe(
        response => {
          this.alertsService.addAlert('success', 'Successfully added positions to test: '+ this.test.name);
          this.emitter.emit(this.selectedPositions);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
    } else {
      this.testsService.removePositions(this.test.id, this.selectedPositions).subscribe(
        response => {
          this.alertsService.addAlert('success', 'Successfully removed positions from test: '+this.test.name);
          this.emitter.emit(this.selectedPositions);
        },
        error => {
          this.alertsService.addAlert('danger', error.error);
        }
      );
    }
  }

  setTestAndPositions(test: Test, avaliablePositions: String[], isAddingPositions: boolean){
    this.test = test;
    this.potentialPositions = avaliablePositions;
    this.addingPositions = isAddingPositions;
    if(this.addingPositions){
      this.title = "Add positions to test.";
    } else {
      this. title = "Remove positions from test";
    }
  }

}
