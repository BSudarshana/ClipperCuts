import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Invoice} from '../../../entity/invoice';
import {Payment, PaymentCreateRequest, Paymentmethod} from '../../../entity/payment';
import {PaymentService} from '../../../service/PaymentService';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  form: FormGroup;
  searchForm: FormGroup;

  unpaidInvoices: Invoice[] = [];
  paymentMethods: Paymentmethod[] = [];
  payments: Payment[] = [];
  selectedPayment: Payment | null = null;
  loading = false;

  displayedColumns = [
    'receiptnumber', 'paymentDate', 'invoicenumber',
    'customer', 'amount', 'paymentmethod', 'actions'
  ];
  dataSource = new MatTableDataSource<Payment>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private formBuilder: FormBuilder,
    private paymentService: PaymentService,
    private dialog: MatDialog
  ) {
    this.form = this.formBuilder.group({
      invoice: [null, Validators.required],
      amount: [{value: 0, disabled: true}, [Validators.required, Validators.min(0.01)]],
      paymentmethod: [null, Validators.required],
      remarks: ['', Validators.maxLength(255)]
    });

    this.searchForm = this.formBuilder.group({
      receiptnumber: ['']
    });
  }

  async ngOnInit(): Promise<void> {
    this.form.controls['invoice'].valueChanges.subscribe(
      (invoice: Invoice | null) =>
        this.form.controls['amount'].setValue(Number(invoice?.finalAmount ?? 0))
    );
    await this.loadInitialData();
  }

  private async loadInitialData(): Promise<void> {
    this.loading = true;
    try {
      const [invoices, methods, payments] = await Promise.all([
        this.paymentService.getUnpaidInvoices(),
        this.paymentService.getPaymentMethods(),
        this.paymentService.getAll()
      ]);
      this.unpaidInvoices = invoices;
      this.paymentMethods = methods;
      this.setPayments(payments);
    } catch (error: any) {
      this.showMessage('Payment', this.errorText(error));
    } finally {
      this.loading = false;
    }
  }

  private setPayments(payments: Payment[]): void {
    this.payments = payments;
    this.dataSource = new MatTableDataSource(payments);
    this.dataSource.paginator = this.paginator;
  }

  async add(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.showMessage(
        'Payment Not Saved',
        'Select an unpaid invoice and a payment method.'
      );
      return;
    }

    const invoice: Invoice = this.form.controls['invoice'].value;
    const method: Paymentmethod = this.form.controls['paymentmethod'].value;
    if (!invoice?.id || !method?.id) {
      this.showMessage('Payment Not Saved', 'The invoice or payment method has no ID.');
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Receive Payment',
        message: `Receive Rs. ${Number(invoice.finalAmount).toFixed(2)} for invoice ${invoice.invoicenumber}?`
      }
    });

    confirmation.afterClosed().subscribe(async result => {
      if (!result) {
        return;
      }

      const request: PaymentCreateRequest = {
        invoiceId: invoice.id!,
        paymentmethodId: method.id,
        amount: Number(invoice.finalAmount),
        remarks: this.form.controls['remarks'].value?.trim() || null
      };

      try {
        const response = await this.paymentService.add(request);
        if (response.errors) {
          this.showMessage('Payment Not Saved', response.errors);
          return;
        }

        const paymentId = Number(response.id);
        this.selectedPayment = await this.paymentService.getById(paymentId);
        this.showMessage('Payment', 'Payment saved successfully. The receipt is ready to print.');
        this.form.reset({invoice: null, amount: 0, paymentmethod: null, remarks: ''});
        await this.refreshLists();
      } catch (error: any) {
        this.showMessage('Payment Not Saved', this.errorText(error));
      }
    });
  }

  async search(): Promise<void> {
    try {
      const receiptNumber = this.searchForm.controls['receiptnumber'].value ?? '';
      this.setPayments(await this.paymentService.getAll(receiptNumber));
    } catch (error: any) {
      this.showMessage('Payment Search', this.errorText(error));
    }
  }

  async clearSearch(): Promise<void> {
    this.searchForm.reset();
    this.setPayments(await this.paymentService.getAll());
  }

  async selectPayment(payment: Payment): Promise<void> {
    if (!payment.id) {
      return;
    }
    try {
      this.selectedPayment = await this.paymentService.getById(payment.id);
    } catch (error: any) {
      this.showMessage('Receipt', this.errorText(error));
    }
  }

  async printReceipt(payment?: Payment): Promise<void> {
    if (payment) {
      await this.selectPayment(payment);
    }
    if (!this.selectedPayment) {
      this.showMessage('Receipt', 'Select a payment before printing.');
      return;
    }

    setTimeout(() => window.print());
  }

  clear(): void {
    this.form.reset({invoice: null, amount: 0, paymentmethod: null, remarks: ''});
  }

  private async refreshLists(): Promise<void> {
    const [invoices, payments] = await Promise.all([
      this.paymentService.getUnpaidInvoices(),
      this.paymentService.getAll()
    ]);
    this.unpaidInvoices = invoices;
    this.setPayments(payments);
  }

  private showMessage(heading: string, message: string): void {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: {heading, message}
    });
  }

  private errorText(error: any): string {
    return error?.error?.errors || error?.error?.message ||
      error?.message || 'Unable to communicate with the server.';
  }
}
