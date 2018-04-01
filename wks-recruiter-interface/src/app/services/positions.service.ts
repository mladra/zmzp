import { Injectable } from '@angular/core';
import { Observable } from "rxjs/Observable";
import { of } from "rxjs/observable/of";
import { Position } from "../entities/position";
import { POSITIONS } from "../entities/mock_positions";

@Injectable()
export class PositionsService {

  constructor() { }

  getPositions(): Observable<Position[]> {
    return of(POSITIONS);
  }

}
