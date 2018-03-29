import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

    rootUrl: string;

    constructor(private http: HttpClient) {
        this.rootUrl = 'http://localhost:8080';
    }

    login(login: string, password: string) {
        return this.http.post(this.rootUrl + '/login', {
            login: login,
            password: password
        }, {
            observe: 'response'
        });
    }

    logout() {
        if (localStorage.getItem('Token')) {
            localStorage.removeItem('Token');
            this.http.post(this.rootUrl + '/logout', null, { observe: 'response' }).subscribe();
        }
    }

    getToken() {
        return localStorage.getItem('Token');
    }

    saveToken(response: any) {
        localStorage.setItem('Token', response.headers.get('Authorization'));
    }
}
