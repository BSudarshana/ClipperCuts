import { Appointment } from './appointment';
import { Promotion } from './promotion';

export interface Paymentstatus {
  id: number;
  name: string;
}
export interface InvoiceItem {
  id: number;
  itemId: number;
  locationId: number;
  itemnumber: string;
  itemName: string;
  locationName: string;
  quantity: number;
  price: number;
  discount: number;
  subtotal: number;
}
export interface Invoice {
  id?: number;
  invoicenumber: string;
  invoicedate: string;
  totalamount: number;
  discount: number;
  finalAmount: number;
  invoicetype: string;
  paymentstatus: Paymentstatus;
  appointment?: Appointment | null;
  promotion?: Promotion | null;
  customerId?: number | null;
  customerName?: string | null;
  customerMobile?: string | null;
  invoiceItems?: InvoiceItem[];
  createdByUsername?: string;
}
export interface InvoiceCreateRequest {
  appointmentId: number;
  discount: number;
  promotionId?: number | null;
}
