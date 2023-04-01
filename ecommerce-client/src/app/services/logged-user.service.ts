import { Injectable } from '@angular/core';
import { User } from '../objects/User';

@Injectable({
  providedIn: 'root'
})
export class LoggedUserService {

  constructor() { }

  private loggedUser: User | undefined;

  getAttribute() {
    return this.loggedUser;
  }

  setAttribute(value: User) {
    this.loggedUser = value;
  }
}
