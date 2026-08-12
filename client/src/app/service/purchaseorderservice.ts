import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Purchaseorder} from '../entity/purchaseorder';

@Injectable({providedIn: 'root'})
export class PurchaseorderService {
  private readonly url = 'http://localhost:8080/purchaseorders';

  constructor(private http: HttpClient) {}

  async getAll(query = ''): Promise<Purchaseorder[]> {
    return (await this.http.get<Purchaseorder[]>(this.url + query).toPromise()) ?? [];
  }

  async add(purchaseorder: Purchaseorder): Promise<any> {
    return this.http.post<any>(this.url, purchaseorder).toPromise();
  }

  async update(purchaseorder: Purchaseorder): Promise<any> {
    return this.http.put<any>(this.url, purchaseorder).toPromise();
  }

  async delete(id: number): Promise<any> {
    return this.http.delete<any>(`${this.url}/${id}`).toPromise();
  }
}
