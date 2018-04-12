import { Injectable } from '@angular/core';
import { HttpModule } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver/FileSaver';

@Injectable()
export class TestsService {

  rootUrl: string;
  header: HttpHeaders;

  constructor(private http: HttpClient) {
    this.rootUrl = 'http://localhost:8080/tests';
  }

  addPositions(testId: String, positions: String[]) {
    return this.http.put(this.rootUrl + "/addPosition/" + testId, positions, { observe: 'response' });
  }

  removePositions(testId: String, positions: String[]) {
    return this.http.put(this.rootUrl + "/removePosition/" + testId, positions, { observe: 'response' });
  }

  deleteTest(testId: String) {
    return this.http.delete(this.rootUrl + "/" + testId, { observe: 'response' });
  }

  getAll() {
    return this.http.get(this.rootUrl, { observe: 'response' });
  }

  getPDF(testId: String) {
    // const headers = new HttpHeaders();
    // headers.append('Accept', 'application/pdf');
    // return this.http.get(this.rootUrl + "/" + testId + "/pdf", {headers: headers} ).toPromise()
    // .then(response => this.saveToFileSystem(response, "application/pdf"))
    // .catch(err => this.saveToFileSystem(err,"application/pdf"));
  }

  getXLS(testId: String) {
    // const headers = new HttpHeaders();
    // headers.append('Accept', 'application/vnd.ms-excel');
    // return this.http.get(this.rootUrl + "/" + testId + "/xls", {observe: 'response'}).toPromise().then(response => this.saveToFileSystem(response, "application/vnd.ms-excel"));
  }

  saveToFileSystem(response, type) {
    console.log("saving")
    const contentDispositionHeader: string = response.headers.get('Content-Disposition');
    const parts: string[] = contentDispositionHeader.split(';');
    const filename = parts[1].split('=')[1];
    const blob = new Blob([response._body],{type: type});
    saveAs(blob, "lol");
  }

}
