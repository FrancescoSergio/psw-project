import { environment } from '../../environments/environment';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product } from "../objects/Product";
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class ProductService{
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){


  }

  public getProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/product/all`);
  }

  public addProduct(product: Product): Observable<Product[]>{
    return this.http.post<Product[]>(`${this.apiServerUrl}/product/add`,product);
  }

  public updateProduct(product: Product): Observable<Product[]>{
    return this.http.put<Product[]>(`${this.apiServerUrl}/product/update`,product);
  }

  public deleteProduct(productId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/product/delete?id=${productId}`);
  }


}
