import {Injectable} from '@angular/core';
import 'rxjs/add/operator/toPromise';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {saveAs} from 'file-saver/FileSaver';
import { TestAttempt } from '../../entities/test.attempt';

@Injectable()
export class TestsService {

    rootUrl: string;
    header: HttpHeaders;

    constructor(private http: HttpClient) {
        this.rootUrl = 'http://localhost:8080/tests';
    }

    addPositions(testId: String, positions: String[]) {
        return this.http.put(this.rootUrl + '/addPosition/' + testId, positions, {observe: 'response'});
    }

    removePositions(testId: String, positions: String[]) {
        return this.http.put(this.rootUrl + '/removePosition/' + testId, positions, {observe: 'response'});
    }

    deleteTest(testId: String) {
        return this.http.delete(this.rootUrl + '/' + testId, {observe: 'response'});
    }

    getById(id) {
        return this.http.get(this.rootUrl + '/' + id, {observe: 'response'});
    }

    getPDF(testId: String, testName: String) {
        return this.http.get(this.rootUrl + '/' + testId + '/pdf',
            {responseType: 'blob'}).subscribe(response => {
            const blob = new Blob([response], {type: 'application/pdf'});
            saveAs(blob, testName + '.pdf');
        });
    }

    getXLS(testId: String, testName: String) {
        return this.http.get(this.rootUrl + '/' + testId + '/xls',
            {responseType: 'blob'}).subscribe(response => {
            const blob = new Blob([response], {type: 'application/vnd.ms-excel'});
            saveAs(blob, testName + '.xls');
        });
    }

    getCandidateTests() {
        return this.http.get(this.rootUrl, {params: {role: 'Candidate'}});
    }

    getEditorTests() {
        return this.http.get(this.rootUrl, {params: {role: 'Editor'}});
    }

    solveTest(testId, testAttempt: TestAttempt) {
        return this.http.post(this.rootUrl + '/' + testId, testAttempt, {observe: 'response'});
    }
}
