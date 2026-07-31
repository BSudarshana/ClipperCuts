import {Invoice} from './invoice';

export interface Paymentmethod {
  id: number;
  name: string;
}

export interface Payment {
  id?: number;
  receiptnumber: string;
  paymentDate: string;
  amount: number;
  remarks?: string | null;
  invoice: Invoice;
  paymentmethod: Paymentmethod;
}

export interface PaymentCreateRequest {
  invoiceId: number;
  paymentmethodId: number;
  amount: number;
  remarks?: string | null;
}
