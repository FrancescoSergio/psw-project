import { LoggedUserService } from './../../services/logged-user.service';
import { Component } from '@angular/core';
import { User } from 'src/app/objects/User';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private loggedUserService: LoggedUserService){

  }

  getAttribute(): any {
    return this.loggedUserService.getAttribute();
  }

  setAttribute(user:User):void {
    this.loggedUserService.setAttribute(user);
  }

  public logOut(){
    var emptyUser:any;
    this.setAttribute(emptyUser);
    console.log(this.loggedUserService.getAttribute())
  }
}

