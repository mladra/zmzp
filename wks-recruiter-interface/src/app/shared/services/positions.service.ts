import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Position } from "../../entities/position";


@Injectable()
export class PositionsService {

    rootUrl: string; 

    constructor(private http: HttpClient) {
        this.rootUrl = 'http://localhost:8080/positions';
    }

    addPosition(position){
        return this.http.post(this.rootUrl, position, {observe: 'response'});
    }

    modifyPosition(name: string, value: boolean){
        return this.http.put(this.rootUrl+'/'+name, value, {observe: 'response'});
    }

    getAll(){
        return this.http.get(this.rootUrl, { observe: 'response' });
    }

}
