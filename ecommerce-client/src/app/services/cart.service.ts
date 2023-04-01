import { CartItem } from './../objects/CartItem';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cart } from '../objects/Cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  public getCarts(): Observable<Cart[]>{
    return this.http.get<Cart[]>(`${this.apiServerUrl}/cart/all`);
  }

  public getCart(email:string): Observable<Cart>{
    return this.http.get<Cart>(`${this.apiServerUrl}/cart/find?email=${email}`);
  }

  public getCartItems(id:number): Observable<CartItem[]>{
    return this.http.get<CartItem[]>(`${this.apiServerUrl}/cart/itemslist?id=${id}`);
  }

  public addItemToCart(cartItem: CartItem): Observable<CartItem>{
    return this.http.put<CartItem>(`${this.apiServerUrl}/cart/additem`,cartItem);
  }

  public completePurchase(cart:Cart): Observable<Cart>{
    return this.http.post<Cart>(`${this.apiServerUrl}/cart/purchased`,cart);
  }

  public addCart(cart: Cart): Observable<Cart[]>{
    return this.http.post<Cart[]>(`${this.apiServerUrl}/cart/add`,cart);
  }

  public updateCart(cart: Cart): Observable<Cart[]>{
    return this.http.put<Cart[]>(`${this.apiServerUrl}/cart/update`,cart);
  }

  public removeItemFromCart(cartItem: CartItem): Observable<CartItem>{
    return this.http.put<CartItem>(`${this.apiServerUrl}/cart/removeitems`,cartItem);
  }

  public deleteCart(cartId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/cart/delete?id=${cartId}`);
  }
}
