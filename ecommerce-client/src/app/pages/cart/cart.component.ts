import { CartItem } from './../../objects/CartItem';
import { LoggedUserCartService } from './../../services/logged-user-cart.service';
import { CartService } from './../../services/cart.service';
import { Component,  OnInit } from '@angular/core';
import { Cart } from 'src/app/objects/Cart';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit{

  public loggedUserCart: Cart | any;
  public isLogged: boolean = false;
  public cartItems: CartItem[] | undefined;
  public quantityToRemove:number = 0;

  constructor(private loggedUserCartService:LoggedUserCartService,private cartService:CartService){

  }

  ngOnInit():void{
    this.loggedUserCart = this.loggedUserCartService.getAttribute();
    if(this.loggedUserCart !== undefined){
      this.isLogged = true;
      this.cartService.getCartItems(this.loggedUserCart.id).subscribe((response)=>{
        this.cartItems = response;
      });
    }
  }

  public setQuantityToRemove(num:string){
    this.quantityToRemove = Number(num);
  }

  public completePurchase(){
    this.cartService.completePurchase(this.loggedUserCart).subscribe((response)=>{
      alert("Acquisto completato con successo!");
    })
  }

  public removeItemFromCart(event: Event, cartItem:CartItem, quantityToRemove:string){
    event.preventDefault();
    const newCartItem: CartItem = {
      id:cartItem.id,
      product:cartItem.product,
      quantity:Number(quantityToRemove),
      cart:this.loggedUserCart
    }
    this.cartService.removeItemFromCart(newCartItem).subscribe((response) => {
      console.log(response);
      alert("Prodotto/i rimosso/i dal carrello!");
    });
  }
}
