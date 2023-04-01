import { Injectable } from '@angular/core';
import { Cart } from '../objects/Cart';

@Injectable({
  providedIn: 'root'
})
export class LoggedUserCartService {

  constructor() { }

  private loggedUserCart: Cart | undefined;

  getAttribute() {
    return this.loggedUserCart;
  }

  setAttribute(value: Cart) {
    this.loggedUserCart = value;
  }
}
