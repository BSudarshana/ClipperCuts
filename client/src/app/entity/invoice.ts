import {Appointment} from './appointment';
import {Promotion} from './promotion';

export interface Paymentstatus {
  id: number;
  name: string;
}

export interface Invoice {
  id?: number;
  invoicenumber: string;
  invoicedate: string;
  totalamount: number;
  discount: number;
  tax: number;
  finalAmount: number;
  paymentstatus: Paymentstatus;
  appointment: Appointment;
  promotion?: Promotion | null;
}

export interface InvoiceCreateRequest {
  appointmentId: number;
  discount: number;
  tax: number;
  promotionId?: number | null;
}
