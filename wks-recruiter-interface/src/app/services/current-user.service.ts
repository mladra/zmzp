import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Account } from "../entities/account";

@Injectable()
export class CurrentUserService {

  private mockUser: Account;
  constructor() {
    this.mockUser = {
      id: "asd876afsaddf6ght",
      login: "john.smith@wks-recruiter.com",
      name: "John",
      surname: "Smith",
      password: "ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae",
      roles: ["Editor", "Moderator"]
    }
  }

  getCurrentUser(): Observable<Account> {
    return of(this.mockUser);
  }

}
