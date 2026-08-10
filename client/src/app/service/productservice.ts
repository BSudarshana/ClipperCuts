import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiResponse, Product, ProductRequest} from '../entity/product';

@Injectable({providedIn: 'root'})
export class ProductService {
  private readonly url = 'http://localhost:8080/items';
  constructor(private http: HttpClient) {}
  getAll(query = ''): Promise<Product[]> { return this.http.get<Product[]>(this.url + query).toPromise().then(v => v ?? []); }
  getById(id: number): Promise<Product | undefined> { return this.http.get<Product>(`${this.url}/${id}`).toPromise(); }
  add(value: ProductRequest): Promise<ApiResponse | undefined> { return this.http.post<ApiResponse>(this.url, value).toPromise(); }
  update(id: number, value: ProductRequest): Promise<ApiResponse | undefined> { return this.http.put<ApiResponse>(`${this.url}/${id}`, value).toPromise(); }
  delete(id: number): Promise<ApiResponse | undefined> { return this.http.delete<ApiResponse>(`${this.url}/${id}`).toPromise(); }
}
