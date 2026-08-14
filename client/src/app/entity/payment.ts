export interface Paymentmethod {
  id: number;
  name: string;
}
export interface PayableInvoice {
  id: number;
  invoicenumber: string;
  invoicetype: string;
  invoicedate: string;
  customerName: string;
  customerMobile?: string | null;
  finalAmount: number;
  paidAmount: number;
  balance: number;
  paymentStatus: string;
}
export interface Payment {
  id: number;
  receiptnumber: string;
  paymentDate: string;
  invoiceId: number;
  invoicenumber: string;
  invoiceType: string;
  invoiceDate: string;
  customerName: string;
  customerMobile?: string | null;
  paymentmethodId: number;
  paymentmethodName: string;
  amount: number;
  invoiceAmount: number;
  paidAmount: number;
  balance: number;
  remarks?: string | null;
  receivedByUsername?: string | null;
}
export interface PaymentCreateRequest {
  invoiceId: number;
  paymentmethodId: number;
  amount: number;
  remarks?: string | null;
}
