import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../../router.animations';
import { Test } from '../../../entities/test';
import { TestsService } from '../../../shared/services/tests.service';
import { AlertsService } from '../../../services/alerts.service';
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";


@Component({
  selector: 'app-tests-list',
  templateUrl: './tests-list.component.html',
  styleUrls: ['./tests-list.component.scss'],
  animations: [routerTransition()]
})
export class TestsListComponent implements OnInit {

  public tests: Test[];

  constructor(private alertsService: AlertsService,
              private testsService: TestsService,
              //private modalService: NgbModal
            ) { }

  ngOnInit() {
    this.getAllTests();
  }

  addPosition(){

  }

  removePosition(){
    
  }
  
  getAllTests(){
    //TODO implement when endpoint is ready
  }

}
