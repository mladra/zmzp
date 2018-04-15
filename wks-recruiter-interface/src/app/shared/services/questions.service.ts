import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable()
export class QuestionsService {

  rootUrl: string;
  header: HttpHeaders;

  constructor(private http: HttpClient) {
    this.rootUrl = 'http://localhost:8080/questions';
  }

  setQuestions(testId, questions) {
    return this.http.put(this.rootUrl + '/' + testId, questions, { observe: 'response' });
  }
}
