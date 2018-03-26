import { Injectable } from '@angular/core';

@Injectable()
export class AlertsService {

  alerts: Array<any> = [];
  constructor() {
  }

  public getAlerts() {
    return this.alerts;
  }

  public closeAlert(alert: any) {
    const index: number = this.alerts.indexOf(alert);
    this.alerts.splice(index, 1);
  }

  public addAlert(type: String, message: String) {
    let lastId: number;
    let alertToAdd: any;
    if (this.alerts.length == 0) {
      lastId = 0;
      alertToAdd = {
        id: 1,
        type: type,
        message: message
      };
    } else {
      lastId = this.alerts.sort(x => x.id)[this.alerts.length - 1];
      alertToAdd = {
        id: (lastId + 1),
        type: type,
        message: message
      };
    }
    this.alerts.push(alertToAdd);
    setTimeout(() => {
      this.closeAlert(alertToAdd);
    }, 10000);
  }

}
