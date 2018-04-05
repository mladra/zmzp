import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable()
export class TestsService {

  rootUrl: string;
  header: HttpHeaders;

  constructor(private http: HttpClient) {
    this.rootUrl = 'http://localhost:8080/tests';
  }
  
  addPosition(testId: String, positions: String[]){
    return this.http.put(this.rootUrl+"/addPosition/"+testId, positions, {observe: 'response'});
  }

  removePosition(testId: String, positions: String[]){
    return this.http.put(this.rootUrl+"/removePosition/"+testId, positions, {observe: 'response'});
  }

  

}
