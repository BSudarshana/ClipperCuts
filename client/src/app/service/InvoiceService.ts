import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {Appointment} from '../entity/appointment';
import {Invoice, InvoiceCreateRequest} from '../entity/invoice';
import {Promotion} from '../entity/promotion';
import {ApiResponse} from './apiresponse';

@Injectable({providedIn: 'root'})
export class InvoiceService {
  private readonly baseUrl = 'http://localhost:8080/invoices';

  constructor(private http: HttpClient) {}

  async getAll(query = ''): Promise<Invoice[]> {
    return (await firstValueFrom(
      this.http.get<Invoice[]>(`${this.baseUrl}${query}`)
    )) ?? [];
  }

  async getEligibleAppointments(): Promise<Appointment[]> {
    return (await firstValueFrom(
      this.http.get<Appointment[]>(`${this.baseUrl}/eligible-appointments`)
    )) ?? [];
  }

  async getPromotions(): Promise<Promotion[]> {
    return (await firstValueFrom(
      this.http.get<Promotion[]>('http://localhost:8080/promotions')
    )) ?? [];
  }

  async add(request: InvoiceCreateRequest): Promise<ApiResponse> {
    return firstValueFrom(
      this.http.post<ApiResponse>(this.baseUrl, request)
    );
  }

  async delete(id: number): Promise<ApiResponse> {
    return firstValueFrom(
      this.http.delete<ApiResponse>(`${this.baseUrl}/${id}`)
    );
  }

  buildSearchQuery(invoiceNumber: string, invoiceDate: string): string {
    let params = new HttpParams();
    if (invoiceNumber.trim()) {
      params = params.set('invoicenumber', invoiceNumber.trim());
    }
    if (invoiceDate) {
      params = params.set('invoicedate', invoiceDate);
    }
    const query = params.toString();
    return query ? `?${query}` : '';
  }
}
