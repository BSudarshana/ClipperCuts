import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {ApiResponse, Category, Lookup, Product, ProductRequest, Subcategory} from '../../../entity/product';
import {ProductService} from '../../../service/productservice';
import {ProductLookupService} from '../../../service/productlookupservice';
import {AuthorizationManager} from '../../../service/authorizationmanager';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {
  form: FormGroup;
  searchForm: FormGroup;
  products: Product[] = [];
  data = new MatTableDataSource<Product>([]);
  displayedColumns = ['itemnumber', 'name', 'category', 'brand', 'sprice', 'totalStock', 'status'];
  categories: Category[] = [];
  subcategories: Subcategory[] = [];
  statuses: Lookup[] = [];
  brands: Lookup[] = [];
  unittypes: Lookup[] = [];
  selected?: Product;
  snapshot = '';
  loading = false;

  // Button enable flags
  enaadd: boolean = false;
  enaupd: boolean = false;
  enadel: boolean = false;

  hasInsertAuthority = false;
  hasUpdateAuthority = false;
  hasDeleteAuthority = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private fb: FormBuilder, private productsApi: ProductService,
              private lookups: ProductLookupService, private dialog: MatDialog,
              private auth: AuthorizationManager) {
    this.form = this.fb.group({
      itemnumber: ['', [Validators.required, Validators.maxLength(45)]],
      name: ['', [Validators.required, Validators.maxLength(45)]],
      dointroduced: [null], pprice: [null, [Validators.required, Validators.min(0)]],
      sprice: [null, [Validators.required, Validators.min(0)]],
      rop: [0, [Validators.required, Validators.min(0)]], category: [null, Validators.required],
      subcategory: [null, Validators.required], itembrand: [null, Validators.required],
      unittype: [null, Validators.required], itemstatus: [null, Validators.required],
      totalStock: [{value: 0, disabled: true}]
    });
    this.searchForm = this.fb.group({itemnumber: new FormControl(''), name: new FormControl(''), statusId: new FormControl(null)});
  }

  async ngOnInit(): Promise<void> {
    this.setAuthorities();
    try {
      [this.categories, this.statuses, this.brands, this.unittypes] = await Promise.all([
        this.lookups.categories(), this.lookups.statuses(), this.lookups.brands(), this.lookups.unittypes()
      ]);
      await this.load();
    } catch (e: any) { this.show('Product', this.errorText(e)); }
  }

  private setAuthorities(): void {
    const raw = this.auth.getAuthorities();
    const a = raw && Array.isArray(raw) ? this.auth.extractAuthorities(raw) : [];
    const productModule = (x: {module: string}) => x.module === 'product' || x.module === 'item';
    this.hasInsertAuthority = a.some(x => productModule(x) && x.operation === 'insert');
    this.hasUpdateAuthority = a.some(x => productModule(x) && x.operation === 'update');
    this.hasDeleteAuthority = a.some(x => productModule(x) && x.operation === 'delete');
  }

  async load(query = ''): Promise<void> {
    this.loading = true;
    try {
      this.products = await this.productsApi.getAll(query);
      this.data.data = this.products;
      setTimeout(() => this.data.paginator = this.paginator);
      this.enableButtons(true, false, false);
    }
    finally { this.loading = false; }
  }

  async categoryChanged(category: Category | null, keepSelection = false): Promise<void> {
    this.subcategories = category ? await this.lookups.subcategories(category.id) : [];
    if (!keepSelection) this.form.controls['subcategory'].setValue(null);
  }

  async select(row: Product): Promise<void> {
    this.selected = row;
    const category = this.categories.find(v => v.id === row.subcategory.category.id) ?? row.subcategory.category;
    await this.categoryChanged(category, true);
    this.form.patchValue({
      itemnumber: row.itemnumber, name: row.name, dointroduced: row.dointroduced,
      pprice: row.pprice, sprice: row.sprice, rop: row.rop, category,
      subcategory: this.subcategories.find(v => v.id === row.subcategory.id),
      itembrand: this.brands.find(v => v.id === row.itembrand.id),
      unittype: this.unittypes.find(v => v.id === row.unittype.id),
      itemstatus: this.statuses.find(v => v.id === row.itemstatus.id), totalStock: row.totalStock ?? 0
    });
    this.form.markAsPristine(); this.snapshot = JSON.stringify(this.payload());
    this.enableButtons(false, true, true);
  }

  payload(): ProductRequest {
    const v = this.form.getRawValue();
    const introduced = v.dointroduced instanceof Date
      ? `${v.dointroduced.getFullYear()}-${String(v.dointroduced.getMonth() + 1).padStart(2, '0')}-${String(v.dointroduced.getDate()).padStart(2, '0')}`
      : v.dointroduced;
    return {itemnumber: v.itemnumber.trim(), name: v.name.trim(), dointroduced: introduced,
      pprice: Number(v.pprice), sprice: Number(v.sprice), rop: Number(v.rop),
      itemstatusId: v.itemstatus.id, unittypeId: v.unittype.id,
      itembrandId: v.itembrand.id, subcategoryId: v.subcategory.id};
  }

  async add(): Promise<void> {
    if (!this.validate()) return;
    if (!await this.confirm('Product Add', `Add ${this.form.value.name}?`)) return;
    await this.mutate(() => this.productsApi.add(this.payload()), 'Product saved successfully');
  }

  async update(): Promise<void> {
    if (!this.selected || !this.validate()) return;
    if (this.snapshot === JSON.stringify(this.payload())) { this.show('Product Update', 'Nothing changed'); return; }
    if (!await this.confirm('Product Update', `Save changes to ${this.form.value.name}?`)) return;
    await this.mutate(() => this.productsApi.update(this.selected!.id!, this.payload()), 'Product updated successfully');
  }

  async delete(): Promise<void> {
    if (!this.selected || !await this.confirm('Product Delete', `Delete ${this.selected.name}?`)) return;
    await this.mutate(() => this.productsApi.delete(this.selected!.id!), 'Product deleted successfully');
  }

  private validate(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.show('Product', 'Complete all required fields with valid values');
      return false;
    }
    return true;
  }

  private async mutate(action: () => Promise<ApiResponse | undefined>, success: string): Promise<void> {
    try { const r = await action(); if (!r || r.errors) throw new Error(r?.errors || 'No response from server'); this.show('Product', success); this.clear(); await this.load(); }
    catch (e: any) { this.show('Product', this.errorText(e)); }
  }

  // Enable or disable Add, Update, and Delete buttons
  enableButtons(add:boolean, upd:boolean, del:boolean){
    this.enaadd=add;
    this.enaupd=upd;
    this.enadel=del;
  }

  search(): void {
    const v = this.searchForm.value; const p = new URLSearchParams();
    if (v.itemnumber?.trim()) p.set('itemnumber', v.itemnumber.trim());
    if (v.name?.trim()) p.set('name', v.name.trim());
    if (v.statusId) p.set('statusId', String(v.statusId));
    this.load(p.toString() ? `?${p}` : '');
  }

  clearSearch(): void { this.searchForm.reset(); this.load(); }

  clear(): void {
    this.selected = undefined;
    this.snapshot = '';
    this.subcategories = [];
    this.enableButtons(true, false, false);
    this.form.reset({
      rop: 0, totalStock: 0
    });
  }

  private show(heading: string, message: string): void { this.dialog.open(MessageComponent, {width: '500px', data: {heading, message}}); }
  private confirm(heading: string, message: string): Promise<boolean> { return this.dialog.open(ConfirmComponent, {width: '500px', data: {heading, message}}).afterClosed().toPromise().then(Boolean); }
  private errorText(e: any): string { return e?.error?.message || e?.error?.error || e?.message || 'Server request failed'; }


}
