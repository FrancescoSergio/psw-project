import { CartService } from './../../services/cart.service';
import { Product } from './../../objects/Product';
import { LoggedUserCartService } from './../../services/logged-user-cart.service';
import { ProductService } from './../../services/product.service';
import { Component,  OnInit } from '@angular/core';
import { CartItem } from 'src/app/objects/CartItem';
import { Cart } from 'src/app/objects/Cart';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {
  allProducts: any[] = [];
  displayedProducts: any[] = [];
  public loggedUserCart : Cart | any;
  public newquantity:number = 0;

  constructor(private productService: ProductService,private loggedUserCartService: LoggedUserCartService, private cartService:CartService) {}

  ngOnInit() {
    this.loggedUserCart = this.loggedUserCartService.getAttribute();
    this.productService.getProducts().subscribe((products) => {
      this.allProducts = products;
      this.displayedProducts = products;
      console.table(this.displayedProducts);
    });
  }

  public setQuantity(num:string){
    this.newquantity = Number(num);
    console.log(this.newquantity);
  }

  public addItemToCart(event: Event, product:Product){
    event.preventDefault();
    const newCartItem: CartItem = {
      product:product,
      quantity:this.newquantity,
      cart:this.loggedUserCart
    }
    console.log(newCartItem);
    this.cartService.addItemToCart(newCartItem).subscribe((response) => {
      console.log(response);
      alert("Prodotto/i aggiunto/i al carrello!");
      const target = event.target as HTMLButtonElement;
      target.disabled = true;
    });
  }

  public onSubmit(event: Event, boxValue: string): void {
    event.preventDefault();
    this.filterProducts(boxValue);
  }

  filterProducts(searchQuery: string) {
    this.displayedProducts = this.allProducts.filter((product) =>
      product.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }
}
