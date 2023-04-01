import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, Query } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../objects/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){


  }

  public getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServerUrl}/user/all`);
  }

  public getUser(email:string,password:string): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/find?email=${email}&password=${password}`);
  }

  public addUser(email:string,password:string): Observable<User>{
    return this.http.post<User>(`${this.apiServerUrl}/user/add`,{id: null, email: email, password: password})
  }

  public updateUser(user: User): Observable<User[]>{
    return this.http.put<User[]>(`${this.apiServerUrl}/user/update`,user);
  }

  public deleteUser(userId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/user/delete?id=${userId}`);
  }
}
