import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class AccountsService {

    rootUrl: string;

    constructor(private http: HttpClient) {
        this.rootUrl = 'http://localhost:8080/accounts';
    }

    getAll() {
        return this.http.get(this.rootUrl, { observe: 'response' });
    }

    createAccount(account) {
        return this.http.post(this.rootUrl, account, {observe: 'response'});
    }

    editAccount(account) {
        return this.http.put(this.rootUrl + '/edit', account, {observe: 'response'});
    }

    editRoles(login, roles) {
        return this.http.put(this.rootUrl + '/' + login, roles, {observe: 'response'});
    }

    deleteAccount(login) {
        return this.http.put(this.rootUrl + '/delete/' + login, null, {observe: 'response'});
    }
}
