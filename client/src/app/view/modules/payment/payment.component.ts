import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {
  PayableInvoice,
  Payment,
  PaymentCreateRequest,
  Paymentmethod
} from '../../../entity/payment';
import { PaymentService } from '../../../service/PaymentService';
import { ConfirmComponent } from '../../../util/dialog/confirm/confirm.component';
import { MessageComponent } from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})

export class PaymentComponent implements OnInit {
  form: FormGroup;
  searchForm: FormGroup;
  payableInvoices: PayableInvoice[] = [];
  paymentMethods: Paymentmethod[] = [];
  selectedPayment: Payment | null = null;
  loading = false;
  saving = false;
  columns = [
    'receiptnumber',
    'paymentDate',
    'invoicenumber',
    'customer',
    'amount',
    'paymentmethod',
    'balance',
    'actions'
  ];
  dataSource = new MatTableDataSource<Payment>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private service: PaymentService,
    private dialog: MatDialog
  ) {
    this.form = fb.group({
      invoice: [null, Validators.required],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      paymentmethod: [null, Validators.required],
      remarks: ['', Validators.maxLength(255)]
    });
    this.searchForm = fb.group({ receiptnumber: [''] });
    this.form.get('invoice')?.valueChanges.subscribe((i: PayableInvoice | null) => {
      this.form.get('amount')?.setValue(i ? i.balance : null);
      this.applyAmountLimit(i);
    });
  }

  async ngOnInit() {
    await this.load();
  }

  private async load() {
    this.loading = true;
    try {
      const [i, m, p] = await Promise.all([
        this.service.getPayableInvoices(),
        this.service.getPaymentMethods(),
        this.service.getAll()
      ]);
      this.payableInvoices = i;
      this.paymentMethods = m;
      this.setPayments(p);
    } catch (e) {
      this.error(e, 'Unable to load payment data.');
    } finally {
      this.loading = false;
    }
  }

  private applyAmountLimit(i: PayableInvoice | null) {
    const c = this.form.get('amount');
    c?.setValidators([
      Validators.required,
      Validators.min(0.01),
      ...(i ? [Validators.max(Number(i.balance))] : [])
    ]);
    c?.updateValueAndValidity({ emitEvent: false });
  }

  private setPayments(p: Payment[]) {
    this.dataSource = new MatTableDataSource(p);
    this.dataSource.paginator = this.paginator;
  }

  submit() {
    this.form.markAllAsTouched();
    if (this.form.invalid)
      return this.message(
        'Payment Not Saved',
        'Select an invoice and method, then enter an amount within the remaining balance.'
      );
    const v = this.form.getRawValue();
    const invoice: PayableInvoice = v.invoice;
    const method: Paymentmethod = v.paymentmethod;
    this.dialog
      .open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: 'Confirmation - Receive Payment',
          message: `Receive Rs. ${Number(v.amount).toFixed(2)} for ${invoice.invoicenumber}?`
        }
      })
      .afterClosed()
      .subscribe(async (ok) => {
        if (!ok) return;
        const r: PaymentCreateRequest = {
          invoiceId: invoice.id,
          paymentmethodId: method.id,
          amount: Number(v.amount),
          remarks: v.remarks?.trim() || null
        };
        this.saving = true;
        try {
          const created = await this.service.add(r);
          this.selectedPayment = await this.service.getById(Number(created.id));
          this.message('Payment', 'Payment saved successfully. The receipt is ready to print.');
          this.clear();
          await this.refresh();
        } catch (e) {
          this.error(e, 'Payment could not be saved.');
        } finally {
          this.saving = false;
        }
      });
  }

  async search() {
    try {
      this.setPayments(
        await this.service.getAll(this.searchForm.get('receiptnumber')?.value || '')
      );
    } catch (e) {
      this.error(e, 'Payment search failed.');
    }
  }

  async clearSearch() {
    this.searchForm.reset();
    this.setPayments(await this.service.getAll());
  }

  async select(p: Payment) {
    try {
      this.selectedPayment = await this.service.getById(p.id);
    } catch (e) {
      this.error(e, 'Receipt could not be loaded.');
    }
  }

  async print(p?: Payment) {
    if (p) await this.select(p);
    if (!this.selectedPayment) return this.message('Receipt', 'Select a payment before printing.');
    setTimeout(() => window.print());
  }

  clear() {
    this.form.reset({
      invoice: null,
      amount: null,
      paymentmethod: null,
      remarks: ''
    });
  }

  private async refresh() {
    const [i, p] = await Promise.all([this.service.getPayableInvoices(), this.service.getAll()]);
    this.payableInvoices = i;
    this.setPayments(p);
  }

  private message(h: string, m: string) {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: { heading: h, message: m }
    });
  }

  private error(e: any, f: string) {
    this.message('Payment - Error', e?.error?.message || e?.error?.errors || e?.message || f);
  }

}
