import {DatePipe} from '@angular/common';
import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Employee} from 'src/app/entity/employee';
import {Item} from 'src/app/entity/item';
import {Poitem} from 'src/app/entity/poitem';
import {Postatus} from 'src/app/entity/postatus';
import {Purchaseorder} from 'src/app/entity/purchaseorder';
import {Supplier} from 'src/app/entity/supplier';
import {AuthorizationManager} from 'src/app/service/authorizationmanager';
import {EmployeeService} from 'src/app/service/employeeservice';
import {Itemservice} from 'src/app/service/itemservice';
import {Postatusservice} from 'src/app/service/postatusservice';
import {PurchaseorderService} from 'src/app/service/purchaseorderservice';
import {RegexService} from 'src/app/service/regexservice';
import {SupplierService} from 'src/app/service/supplierservice';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';
import {UiAssist} from '../../../util/ui/ui.assist';

@Component({
  selector: 'app-purchaseorder',
  templateUrl: './purchaseorder.component.html',
  styleUrls: ['./purchaseorder.component.css']
})
export class PurchaseorderComponent implements OnInit {
  columns = ['poNumber', 'employee', 'postatus', 'date', 'description', 'totalAmount'];
  headers = ['PO Number', 'Employee', 'Status', 'Date', 'Description', 'Expected Cost'];
  binders = ['poNumber', 'employee.fullname', 'postatus.name', 'date', 'description', 'totalAmount'];
  cscolumns = ['cspoNumber', 'csemployee', 'cspostatus', 'csdate', 'csdescription', 'cstotalAmount'];
  csprompts = ['Search by PO Number', 'Search by Employee', 'Search by Status', 'Search by Date', 'Search by Description', 'Search by Expected Cost'];
  incolumns = ['item', 'quantity', 'unitprice', 'subTotal', 'remove'];

  form: FormGroup;
  innerform: FormGroup;
  csearch: FormGroup;
  ssearch: FormGroup;
  data = new MatTableDataSource<Purchaseorder>([]);
  indata = new MatTableDataSource<Poitem>([]);

  purchaseorders: Purchaseorder[] = [];
  postatuses: Postatus[] = [];
  employees: Employee[] = [];
  suppliers: Supplier[] = [];
  items: Item[] = [];
  regexes: any = {};
  selectedrow?: Purchaseorder;
  oldpurchaseorder?: Purchaseorder;
  today = new Date();
  imageurl = 'assets/pending.gif';
  uiassist = new UiAssist(this);
  enaadd = true;
  enaupd = false;
  enadel = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private pos: PurchaseorderService,
    private poss: Postatusservice,
    private itms: Itemservice,
    private emps: EmployeeService,
    private sups: SupplierService,
    private rs: RegexService,
    private fb: FormBuilder,
    private dg: MatDialog,
    private dp: DatePipe,
    public authService: AuthorizationManager
  ) {
    this.form = this.fb.group({
      poNumber: [{value: 'Generated when saved', disabled: true}],
      date: [this.today, Validators.required],
      totalAmount: [{value: 0, disabled: true}, [Validators.required, Validators.min(0), Validators.max(999999.99)]],
      description: ['', Validators.required],
      postatus: [null, Validators.required],
      employee: [null, Validators.required],
      supplier: [null, Validators.required]
    });
    this.innerform = this.fb.group({
      item: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.min(0.01), Validators.max(999999.99)]],
      unitPrice: [{value: null, disabled: true}, [Validators.required, Validators.min(0), Validators.max(99999999.99)]]
    });
    this.csearch = this.fb.group({
      cspoNumber: new FormControl(), csemployee: new FormControl(), cspostatus: new FormControl(),
      csdate: new FormControl(), csdescription: new FormControl(), cstotalAmount: new FormControl()
    });
    this.ssearch = this.fb.group({spoNumber: new FormControl(), spostatus: new FormControl()});
  }

  ngOnInit(): void { this.initialize(); }

  async initialize(): Promise<void> {
    this.loadTable('');
    const failures: string[] = [];
    const safeLoad = async <T>(label: string, request: Promise<T>, fallback: T): Promise<T> => {
      try {
        return await request;
      } catch (error) {
        failures.push(label);
        console.error(`Unable to load ${label}:`, error);
        return fallback;
      }
    };

    const [statuses, employees, suppliers, items, regexes] = await Promise.all([
      safeLoad('purchase order statuses', this.poss.getAllList(), [] as Postatus[]),
      safeLoad('employees', this.emps.getAll(''), [] as Employee[]),
      safeLoad('suppliers', this.sups.getAll(''), [] as Supplier[]),
      safeLoad('items', this.itms.getAll(''), [] as Item[]),
      safeLoad('purchase order validation rules', this.rs.get('purchaseorder'), {} as any)
    ]);

    this.postatuses = statuses ?? [];
    this.employees = employees ?? [];
    this.suppliers = suppliers ?? [];
    this.items = items ?? [];
    this.regexes = regexes ?? {};
    this.applyRegexValidators();

    if (failures.length) {
      this.showMessage(
        'Purchase Order',
        `Unable to load: ${failures.join(', ')}.`
      );
    }
  }

  private applyRegexValidators(): void {
    const descriptionRegex = this.regexes?.description?.regex;
    if (descriptionRegex) this.form.controls['description'].addValidators(Validators.pattern(descriptionRegex));
    this.form.controls['description'].updateValueAndValidity({emitEvent: false});
  }

  async loadTable(query: string): Promise<void> {
    this.imageurl = 'assets/pending.gif';
    try {
      this.purchaseorders = await this.pos.getAll(query);
      this.data = new MatTableDataSource(this.purchaseorders.slice().reverse());
      this.data.paginator = this.paginator;
      this.imageurl = 'assets/fullfilled.png';
    } catch (error) {
      this.purchaseorders = [];
      this.data.data = [];
      this.imageurl = 'assets/rejected.png';
      this.showMessage('Purchase Order', 'Unable to load purchase orders.');
    }
  }

  filterTable(): void {
    const f = this.csearch.getRawValue();
    const text = (value: any) => String(value ?? '').toLowerCase();
    this.data.filterPredicate = po =>
      (!f.cspoNumber || text(po.poNumber).includes(text(f.cspoNumber))) &&
      (!f.csemployee || text(po.employee?.fullname).includes(text(f.csemployee))) &&
      (!f.cspostatus || text(po.postatus?.name).includes(text(f.cspostatus))) &&
      (!f.csdate || text(po.date).includes(this.formatDate(f.csdate))) &&
      (!f.csdescription || text(po.description).includes(text(f.csdescription))) &&
      (!f.cstotalAmount || Number(po.totalAmount) === Number(f.cstotalAmount));
    this.data.filter = JSON.stringify(f);
  }

  btnSearchMc(): void {
    const value = this.ssearch.getRawValue();
    const params: string[] = [];
    if (value.spoNumber?.trim()) params.push(`po_number=${encodeURIComponent(value.spoNumber.trim())}`);
    if (value.spostatus != null) params.push(`postatusid=${encodeURIComponent(value.spostatus)}`);
    this.csearch.reset();
    this.loadTable(params.length ? `?${params.join('&')}` : '');
  }

  btnSearchClearMc(): void {
    this.confirm('Search Clear', 'Are you sure you want to clear the search?', () => {
      this.csearch.reset(); this.ssearch.reset(); this.loadTable('');
    });
  }

  fillForm(row: Purchaseorder): void {
    const copy: Purchaseorder = JSON.parse(JSON.stringify(row));
    copy.postatus = this.postatuses.find(x => x.id === copy.postatus?.id) ?? copy.postatus;
    copy.employee = this.employees.find(x => x.id === copy.employee?.id) ?? copy.employee;
    copy.supplier = this.suppliers.find(x => x.id === copy.supplier?.id) ?? copy.supplier;
    copy.poitems = copy.poitems ?? [];
    this.selectedrow = row;
    this.oldpurchaseorder = JSON.parse(JSON.stringify(copy));
    this.indata = new MatTableDataSource(copy.poitems.map(x => ({...x})));
    this.form.patchValue(copy);
    this.calculateGrandTotal(false);
    this.form.markAsPristine();
    this.enableButtons(false, true, true);
  }

  add(): void {
    if (!this.validatePurchaseOrder()) return;
    const payload = this.buildPayload();
    this.confirm('Purchase Order Add', 'Are you sure you want to add this purchase order?', async () => {
      try {
        const response = await this.pos.add(payload);
        if (this.responseError(response)) return this.showMessage('Purchase Order Add', this.responseError(response));
        this.showMessage('Purchase Order Add', `Purchase order ${response?.poNumber ?? ''} saved successfully.`);
        await this.afterMutation();
      } catch (error: any) { this.showMessage('Purchase Order Add', this.httpError(error)); }
    });
  }

  update(): void {
    if (!this.oldpurchaseorder?.id || !this.validatePurchaseOrder()) return;
    const payload = this.buildPayload(this.oldpurchaseorder.id);
    if (JSON.stringify(payload) === JSON.stringify(this.oldpurchaseorder)) {
      return this.showMessage('Purchase Order Update', 'Nothing has changed.');
    }
    this.confirm('Purchase Order Update', `Are you sure you want to update purchase order ${payload.poNumber}?`, async () => {
      try {
        const response = await this.pos.update(payload);
        if (this.responseError(response)) return this.showMessage('Purchase Order Update', this.responseError(response));
        this.showMessage('Purchase Order Update', 'Purchase order updated successfully.');
        await this.afterMutation();
      } catch (error: any) { this.showMessage('Purchase Order Update', this.httpError(error)); }
    });
  }

  delete(): void {
    if (!this.oldpurchaseorder?.id) return;
    this.confirm('Purchase Order Delete', `Are you sure you want to delete purchase order ${this.oldpurchaseorder.poNumber}?`, async () => {
      try {
        const response = await this.pos.delete(this.oldpurchaseorder!.id!);
        if (this.responseError(response)) return this.showMessage('Purchase Order Delete', this.responseError(response));
        this.showMessage('Purchase Order Delete', 'Purchase order deleted successfully.');
        await this.afterMutation();
      } catch (error: any) { this.showMessage('Purchase Order Delete', this.httpError(error)); }
    });
  }

  clear(): void {
    this.confirm('Purchase Order Clear', 'Are you sure you want to clear the entered details?', () => this.resetForm());
  }

  btnaddMc(): void {
    if (this.innerform.invalid) {
      this.innerform.markAllAsTouched();
      return this.showMessage('Purchase Order Item', 'Select an item and enter a valid quantity.');
    }
    const value = this.innerform.getRawValue();
    if (this.indata.data.some(line => line.item.id === value.item.id)) {
      return this.showMessage('Purchase Order Item', 'This item has already been added. Remove it first if you need to change its quantity.');
    }
    const unitprice = this.money(value.unitPrice);
    const subTotal = this.money(Number(value.quantity) * unitprice);
    this.indata.data = [...this.indata.data, new Poitem(Number(value.quantity), unitprice, subTotal, value.item)];
    this.calculateGrandTotal();
    this.innerform.reset();
  }

  deleteRaw(line: Poitem): void {
    this.indata.data = this.indata.data.filter(x => x !== line);
    this.calculateGrandTotal();
  }

  filterlinecost(): void {
    const item: any = this.innerform.controls['item'].value;
    const price = Number(item?.pprice ?? item?.purchasePrice ?? item?.price ?? 0);
    this.innerform.controls['unitPrice'].setValue(price.toFixed(2));
  }

  filteritem(): void {
    this.innerform.reset();
    // If Itemservice exposes getItemBySupplier(id), call it here to restrict the item list.
  }

  private validatePurchaseOrder(): boolean {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      this.showMessage('Purchase Order', 'Complete all required fields with valid values.');
      return false;
    }
    if (!this.indata.data.length) {
      this.showMessage('Purchase Order', 'Add at least one item to the purchase order.');
      return false;
    }
    if (this.indata.data.some(line => Number(line.subTotal) > 999999.99) ||
      Number(this.form.getRawValue().totalAmount) > 999999.99) {
      this.showMessage('Purchase Order', 'A line total or the expected cost exceeds the database limit of 999,999.99.');
      return false;
    }
    return true;
  }

  private buildPayload(id?: number): Purchaseorder {
    const value = this.form.getRawValue();
    return {
      ...(id ? {id} : {}),
      ...(id ? {poNumber: value.poNumber} : {}),
      date: this.formatDate(value.date),
      totalAmount: Number(value.totalAmount),
      description: value.description.trim(),
      postatus: {id: value.postatus.id, name: value.postatus.name},
      employee: value.employee,
      supplier: value.supplier,
      poitems: this.indata.data.map(x => new Poitem(
        Number(x.quantity), this.money(x.unitprice), this.money(x.subTotal), x.item, x.id
      ))
    } as Purchaseorder;
  }

  private calculateGrandTotal(markDirty = true): void {
    const total = this.indata.data.reduce((sum, line) => sum + Number(line.subTotal || 0), 0);
    this.form.controls['totalAmount'].setValue(this.money(total));
    if (markDirty && this.oldpurchaseorder) this.form.controls['totalAmount'].markAsDirty();
  }

  private async afterMutation(): Promise<void> { this.resetForm(); await this.loadTable(''); }

  private resetForm(): void {
    this.oldpurchaseorder = undefined;
    this.selectedrow = undefined;
    this.indata.data = [];
    this.form.reset({poNumber: 'Generated when saved', date: this.today, totalAmount: 0});
    this.innerform.reset();
    this.enableButtons(true, false, false);
  }

  private formatDate(value: any): string { return this.dp.transform(value, 'yyyy-MM-dd') ?? ''; }
  private money(value: any): number { return Number(Number(value ?? 0).toFixed(2)); }
  private enableButtons(add: boolean, update: boolean, remove: boolean): void { this.enaadd = add; this.enaupd = update; this.enadel = remove; }
  private responseError(response: any): string { return response?.errors ?? response?.error ?? ''; }
  private httpError(error: any): string { return error?.error?.errors ?? error?.error?.message ?? error?.message ?? 'Server not found.'; }
  private showMessage(heading: string, message: string): void { this.dg.open(MessageComponent, {width: '500px', data: {heading, message}}); }
  private confirm(heading: string, message: string, action: () => void | Promise<void>): void {
    this.dg.open(ConfirmComponent, {width: '500px', data: {heading, message}}).afterClosed().subscribe(result => { if (result) action(); });
  }
}
