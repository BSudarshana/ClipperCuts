import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {
  AvailableSaleItem,
  ProductSaleRequest,
  SaleCustomer,
  SaleLine,
  SaleLookup
} from '../../../entity/productsale';
import { ProductSaleService } from '../../../service/productsaleservice';
import { ConfirmComponent } from '../../../util/dialog/confirm/confirm.component';
import { MessageComponent } from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-productsaleinvoice',
  templateUrl: './productsaleinvoice.component.html',
  styleUrls: ['./productsaleinvoice.component.css']
})

export class ProductsaleinvoiceComponent implements OnInit {
  form: FormGroup;
  lineForm: FormGroup;
  locations: SaleLookup[] = [];
  customers: SaleCustomer[] = [];
  items: AvailableSaleItem[] = [];
  lines: SaleLine[] = [];
  columns = ['itemnumber', 'name', 'available', 'quantity', 'unit', 'price', 'subtotal', 'actions'];
  loading = false;
  saving = false;
  constructor(
    private fb: FormBuilder,
    private service: ProductSaleService,
    private dialog: MatDialog
  ) {
    this.form = fb.group({
      customerId: [null],
      locationId: [null, Validators.required],
      discount: [0, [Validators.required, Validators.min(0)]]
    });
    this.lineForm = fb.group({
      item: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.min(0.01)]]
    });
    this.form.get('discount')?.valueChanges.subscribe(() => this.validateDiscount());
  }
  async ngOnInit() {
    this.loading = true;
    try {
      [this.locations, this.customers] = await Promise.all([
        this.service.getLocations(),
        this.service.getCustomers()
      ]);
    } catch (e) {
      this.error(e, 'Unable to load sale lookups.');
    } finally {
      this.loading = false;
    }
  }
  get selectedItem(): AvailableSaleItem | null {
    return this.lineForm.get('item')?.value || null;
  }
  get total() {
    return Number(this.lines.reduce((s, x) => s + x.subtotal, 0).toFixed(2));
  }
  get finalAmount() {
    return Math.max(
      0,
      Number((this.total - Number(this.form.get('discount')?.value || 0)).toFixed(2))
    );
  }
  async locationChanged() {
    this.lines = [];
    this.items = [];
    this.lineForm.reset();
    const id = Number(this.form.get('locationId')?.value);
    if (id) {
      try {
        this.items = await this.service.getItems(id);
      } catch (e) {
        this.error(e, 'Unable to load products.');
      }
    }
  }
  addLine() {
    this.lineForm.markAllAsTouched();
    if (this.lineForm.invalid) return;
    const item = this.selectedItem!;
    const quantity = Number(this.lineForm.get('quantity')?.value);
    if (this.lines.some((x) => x.itemId === item.itemId))
      return this.message('Product Sale', 'Product has already been added.');
    if (quantity > item.availableQuantity)
      return this.message(
        'Product Sale',
        `Quantity cannot exceed ${item.availableQuantity} ${item.unitType}.`
      );
    this.lines = [
      ...this.lines,
      {
        ...item,
        quantity,
        subtotal: Number((quantity * item.sellingPrice).toFixed(2))
      }
    ];
    this.lineForm.reset();
    this.validateDiscount();
  }
  remove(id: number) {
    this.lines = this.lines.filter((x) => x.itemId !== id);
    this.validateDiscount();
  }
  private validateDiscount() {
    const c = this.form.get('discount');
    if (Number(c?.value || 0) > this.total) c?.setErrors({ exceedsTotal: true });
    else if (c?.hasError('exceedsTotal')) c.setErrors(null);
  }
  submit() {
    this.form.markAllAsTouched();
    if (this.form.invalid)
      return this.message(
        'Product Invoice Not Saved',
        'Select a location and enter a valid discount.'
      );
    if (!this.lines.length)
      return this.message('Product Invoice Not Saved', 'Add at least one product.');
    this.confirm(
      'Confirmation - Product Invoice',
      `Create product invoice for Rs. ${this.finalAmount.toFixed(2)}?`,
      async () => {
        const v = this.form.getRawValue();
        const request: ProductSaleRequest = {
          customerId: v.customerId ? Number(v.customerId) : null,
          locationId: Number(v.locationId),
          discount: Number(v.discount),
          items: this.lines.map((x) => ({
            itemId: x.itemId,
            quantity: x.quantity
          }))
        };
        this.saving = true;
        try {
          const r = await this.service.create(request);
          this.message('Product Invoice', `${r.invoicenumber} created successfully.`);
          this.clear();
        } catch (e) {
          this.error(e, 'Product invoice could not be created.');
        } finally {
          this.saving = false;
        }
      }
    );
  }
  clear() {
    this.form.reset({ customerId: null, locationId: null, discount: 0 });
    this.lineForm.reset();
    this.items = [];
    this.lines = [];
  }
  private message(h: string, m: string) {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: { heading: h, message: m }
    });
  }
  private confirm(h: string, m: string, a: () => void) {
    this.dialog
      .open(ConfirmComponent, {
        width: '500px',
        data: { heading: h, message: m }
      })
      .afterClosed()
      .subscribe((x) => {
        if (x) a();
      });
  }
  private error(e: any, f: string) {
    this.message('Product Sale - Error', e?.error?.message || e?.message || f);
  }
}
