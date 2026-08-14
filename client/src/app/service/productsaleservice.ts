import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import {
  AvailableSaleItem,
  ProductSaleRequest,
  SaleCustomer,
  SaleLookup
} from '../entity/productsale';

@Injectable({ providedIn: 'root' })

export class ProductSaleService {
  private readonly url = 'http://localhost:8080/invoices/product-sales';

  constructor(private http: HttpClient) {}

  async getLocations(): Promise<SaleLookup[]> {
    return (await firstValueFrom(this.http.get<SaleLookup[]>(`${this.url}/locations`))) || [];
  }

  async getCustomers(): Promise<SaleCustomer[]> {
    return (await firstValueFrom(this.http.get<SaleCustomer[]>(`${this.url}/customers`))) || [];
  }

  async getItems(locationId: number): Promise<AvailableSaleItem[]> {
    const params = new HttpParams().set('locationId', locationId);
    return (
      (await firstValueFrom(
        this.http.get<AvailableSaleItem[]>(`${this.url}/available-items`, {
          params
        })
      )) || []
    );
  }
  async create(r: ProductSaleRequest): Promise<any> {
    return firstValueFrom(this.http.post(this.url, r));
  }
}
