import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Appointment, AppointmentServiceLine} from '../../../entity/appointment';
import {Invoice, InvoiceCreateRequest} from '../../../entity/invoice';
import {Promotion} from '../../../entity/promotion';
import {InvoiceService} from '../../../service/InvoiceService';
import {MessageComponent} from '../../../util/dialog/message/message.component';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})

export class InvoiceComponent implements OnInit {
  form: FormGroup;
  searchForm: FormGroup;

  eligibleAppointments: Appointment[] = [];
  promotions: Promotion[] = [];
  invoices: Invoice[] = [];
  selectedServices: AppointmentServiceLine[] = [];
  selectedInvoice: Invoice | null = null;

  displayedColumns = [
    'invoicenumber', 'invoicedate', 'customer',
    'totalamount', 'discount', 'finalAmount', 'paymentstatus'
  ];

  dataSource = new MatTableDataSource<Invoice>([]);
  loading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private formBuilder: FormBuilder,
    private invoiceService: InvoiceService,
    private dialog: MatDialog
  ) {
    this.form = this.formBuilder.group({
      appointment: new FormControl<Appointment | null>(null, Validators.required),
      promotion: new FormControl<Promotion | null>(null),
      totalamount: new FormControl({value: 0, disabled: true}),
      discount: new FormControl(0, [Validators.required, Validators.min(0)]),
      // tax: new FormControl(0, [Validators.required, Validators.min(0)]),
      finalAmount: new FormControl({value: 0, disabled: true})
    });

    this.searchForm = this.formBuilder.group({
      invoicenumber: [''],
      invoicedate: ['']
    });
  }

  async ngOnInit(): Promise<void> {
    this.form.controls['appointment'].valueChanges.subscribe(
      appointment => this.onAppointmentChanged(appointment)
    );
    this.form.controls['discount'].valueChanges.subscribe(() => this.calculateFinalAmount());
    // this.form.controls['tax'].valueChanges.subscribe(() => this.calculateFinalAmount());
    await this.loadInitialData();
  }

  private async loadInitialData(): Promise<void> {
    this.loading = true;
    try {
      const [appointments, promotions, invoices] = await Promise.all([
        this.invoiceService.getEligibleAppointments(),
        this.invoiceService.getPromotions(),
        this.invoiceService.getAll()
      ]);
      this.eligibleAppointments = appointments;
      this.promotions = promotions;
      this.setInvoices(invoices);
    } catch (error: any) {
      this.showMessage('Invoice', this.errorText(error));
    } finally {
      this.loading = false;
    }
  }

  private setInvoices(invoices: Invoice[]): void {
    this.invoices = invoices;
    this.dataSource = new MatTableDataSource(invoices);
    this.dataSource.paginator = this.paginator;
  }

  onAppointmentChanged(appointment: Appointment | null): void {
    this.selectedServices = appointment?.appointmentservices ?? [];
    const total = this.selectedServices.reduce(
      (sum, line) => sum + Number(line.agreedPrice ?? 0), 0
    );
    this.form.controls['totalamount'].setValue(total);
    this.calculateFinalAmount();
  }

  calculateFinalAmount(): void {
    const total = Number(this.form.controls['totalamount'].value ?? 0);
    const discount = Number(this.form.controls['discount'].value ?? 0);
    // const tax = Number(this.form.controls['tax'].value ?? 0);
    this.form.controls['finalAmount'].setValue(
      Math.max(0, total - discount )
    );
  }

  async add(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.showMessage('Invoice Not Saved', 'Select a completed appointment and enter valid amounts.');
      return;
    }

    const appointment: Appointment = this.form.controls['appointment'].value;
    if (!appointment.id) {
      this.showMessage('Invoice Not Saved', 'The selected appointment has no ID.');
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Invoice Add',
        message: `Create an invoice for ${appointment.customer.fullname}?`
      }
    });

    confirmation.afterClosed().subscribe(async result => {
      if (!result) return;

      const promotion: Promotion | null = this.form.controls['promotion'].value;
      const request: InvoiceCreateRequest = {
        appointmentId: appointment.id!,
        discount: Number(this.form.controls['discount'].value ?? 0),
        promotionId: promotion?.id ?? null
      };

      try {
        const response = await this.invoiceService.add(request);
        if (response.errors) {
          this.showMessage('Invoice Not Saved', response.errors);
          return;
        }
        this.showMessage('Invoice', 'Invoice saved successfully.');
        this.clear();
        await this.loadInitialData();
      } catch (error: any) {
        this.showMessage('Invoice Not Saved', this.errorText(error));
      }
    });
  }

  async search(): Promise<void> {
    const value = this.searchForm.getRawValue();
    const query = this.invoiceService.buildSearchQuery(
      value.invoicenumber ?? '', value.invoicedate ?? ''
    );
    try {
      this.setInvoices(await this.invoiceService.getAll(query));
    } catch (error: any) {
      this.showMessage('Invoice Search', this.errorText(error));
    }
  }

  async clearSearch(): Promise<void> {
    this.searchForm.reset();
    this.setInvoices(await this.invoiceService.getAll());
  }

  selectInvoice(invoice: Invoice): void {
    this.selectedInvoice = invoice;
    this.form.reset();
  }

  clear(): void {
    this.form.reset({appointment: null, promotion: null, discount: 0});
    this.selectedServices = [];
    this.selectedInvoice = null;
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
