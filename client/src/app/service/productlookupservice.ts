import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category, Lookup, Subcategory} from '../entity/product';

@Injectable({providedIn: 'root'})
export class ProductLookupService {
  private readonly api = 'http://localhost:8080';
  constructor(private http: HttpClient) {}
  categories(): Promise<Category[]> { return this.http.get<Category[]>(`${this.api}/categories/list`).toPromise().then(v => v ?? []); }
  subcategories(categoryId?: number): Promise<Subcategory[]> {
    const q = categoryId == null ? '' : `?categoryId=${categoryId}`;
    return this.http.get<Subcategory[]>(`${this.api}/subcategories/list${q}`).toPromise().then(v => v ?? []);
  }
  statuses(): Promise<Lookup[]> { return this.http.get<Lookup[]>(`${this.api}/itemsstatuses/list`).toPromise().then(v => v ?? []); }
  brands(): Promise<Lookup[]> { return this.http.get<Lookup[]>(`${this.api}/itemsbrand/list`).toPromise().then(v => v ?? []); }
  unittypes(): Promise<Lookup[]> { return this.http.get<Lookup[]>(`${this.api}/unittypes/list`).toPromise().then(v => v ?? []); }
}
