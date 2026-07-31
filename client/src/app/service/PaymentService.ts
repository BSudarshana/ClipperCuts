import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {Invoice} from '../entity/invoice';
import {Payment, PaymentCreateRequest, Paymentmethod} from '../entity/payment';
import {ApiResponse} from './apiresponse';

@Injectable({providedIn: 'root'})
export class PaymentService {
  private readonly baseUrl = 'http://localhost:8080/payments';

  constructor(private http: HttpClient) {}

  async getAll(receiptNumber = ''): Promise<Payment[]> {
    let params = new HttpParams();
    if (receiptNumber.trim()) {
      params = params.set('receiptnumber', receiptNumber.trim());
    }
    return (await firstValueFrom(
      this.http.get<Payment[]>(this.baseUrl, {params})
    )) ?? [];
  }

  async getById(id: number): Promise<Payment> {
    return firstValueFrom(
      this.http.get<Payment>(`${this.baseUrl}/${id}`)
    );
  }

  async getUnpaidInvoices(): Promise<Invoice[]> {
    return (await firstValueFrom(
      this.http.get<Invoice[]>(`${this.baseUrl}/unpaid-invoices`)
    )) ?? [];
  }

  async getPaymentMethods(): Promise<Paymentmethod[]> {
    return (await firstValueFrom(
      this.http.get<Paymentmethod[]>('http://localhost:8080/paymentmethods/list')
    )) ?? [];
  }

  async add(request: PaymentCreateRequest): Promise<ApiResponse> {
    return firstValueFrom(
      this.http.post<ApiResponse>(this.baseUrl, request)
    );
  }
}
