import { LoggedUserCartService } from './../../services/logged-user-cart.service';
import { Cart } from './../../objects/Cart';
import { CartService } from './../../services/cart.service';
import { LoggedUserService } from './../../services/logged-user.service';
import { UserService } from './../../services/user.service';
import { Component} from '@angular/core';
import { User } from 'src/app/objects/User';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent{

  private email:string="";
  private pw:string="";

  constructor(private userService:UserService,private loggedUserService:LoggedUserService, private cartService:CartService, private loggedUserCartService:LoggedUserCartService){

  }

  getEmail(email:string){
    this.email=email;
  }

  getPassword(password:string){
    this.pw=password;
  }

  public logIn() {
    //aggiorna lo user loggato
    this.userService.getUser(this.email, this.pw).subscribe({
      next: (response: User) => {
        this.loggedUserService.setAttribute(response);
        console.log(this.loggedUserService.getAttribute());
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
    //aggiorna il carrello dello user loggato
    this.cartService.getCart(this.email).subscribe({
      next: (response: Cart) => {
        this.loggedUserCartService.setAttribute(response);
        console.log(this.loggedUserCartService.getAttribute());
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public signUp() {
    //se non esiste già aggiunge un nuovo user alla repository e lo logga
    this.userService.addUser(this.email, this.pw).subscribe({
      next: (response: User) => {
        this.loggedUserService.setAttribute(response);
        console.log(this.loggedUserService.getAttribute());
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }


}
