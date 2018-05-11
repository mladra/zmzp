import { Injectable } from '@angular/core';
import 'rxjs/add/operator/toPromise';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { saveAs } from 'file-saver/FileSaver';
import { Test } from '../../entities/test';
import { TestAttempt } from '../../entities/test.attempt';

@Injectable()
export class TestAttemptsService {

    rootUrl: string;
    header: HttpHeaders;

    constructor(private http: HttpClient) {
        this.rootUrl = 'http://localhost:8080/test-attempts';
    }

    getTestsAttempts() {
        return this.http.get(this.rootUrl + '/all', { observe: 'response' });
    }

    updateTestAttempt(testAttempt) {
        return this.http.put(this.rootUrl, testAttempt, { observe: 'response' });
    }

    sendEmailNotification(email, testAttempt) {
        return this.http.post(this.rootUrl + '/mail/' + email, testAttempt, { observe: 'response' });
    }
}
