import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Paymentmethod} from '../entity/paymentmethod';

@Injectable({providedIn: 'root'})
export class PaymentmethodService {
  private baseUrl = 'http://localhost:8080/paymentmethods';
  constructor(private http: HttpClient) {}
  async getAllList(): Promise<Array<Paymentmethod>> {
    return (await this.http.get<Array<Paymentmethod>>(`${this.baseUrl}/list`).toPromise()) || [];
  }
}
