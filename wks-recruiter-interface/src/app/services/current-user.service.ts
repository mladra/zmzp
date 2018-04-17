import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Account } from '../entities/account';

@Injectable()
export class CurrentUserService {

  private currentUserRoles: Array<String>;

  constructor() {
    this.getCurrentUser().subscribe(x => this.currentUserRoles = x.roles);
  }

  getCurrentUser(): Observable<Account> {
    return of(JSON.parse(localStorage.getItem('currentUser')));
  }

  setCurrentUser(user) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserRoles = user.roles;
  }

  isCurrentUserInRole(role: String) {
    return this.currentUserRoles.includes(role);
  }
}
