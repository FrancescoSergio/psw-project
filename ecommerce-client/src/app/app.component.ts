import { CartService } from './services/cart.service';
import { UserService } from './services/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { Component, OnInit } from '@angular/core';
import { Product } from './objects/Product';
import { User } from './objects/User';
import { Cart } from './objects/Cart';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent{
  title = 'ecommerce-client';

  constructor(){

  }
}
