import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { PayableInvoice, Payment, PaymentCreateRequest, Paymentmethod } from '../entity/payment';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly url = 'http://localhost:8080/payments';

  constructor(private http: HttpClient) {}

  async getAll(n = ''): Promise<Payment[]> {
    let p = new HttpParams();
    if (n.trim()) p = p.set('receiptnumber', n.trim());
    return (await firstValueFrom(this.http.get<Payment[]>(this.url, { params: p }))) || [];
  }

  async getById(id: number): Promise<Payment> {
    return firstValueFrom(this.http.get<Payment>(`${this.url}/${id}`));
  }

  async getPayableInvoices(): Promise<PayableInvoice[]> {
    return (
      (await firstValueFrom(this.http.get<PayableInvoice[]>(`${this.url}/payable-invoices`))) || []
    );
  }
  async getPaymentMethods(): Promise<Paymentmethod[]> {
    return (
      (await firstValueFrom(
        this.http.get<Paymentmethod[]>('http://localhost:8080/paymentmethods/list')
      )) || []
    );
  }
  async add(r: PaymentCreateRequest): Promise<any> {
    return firstValueFrom(this.http.post(this.url, r));
  }
}
