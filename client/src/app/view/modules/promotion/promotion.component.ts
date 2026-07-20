import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AuthorizationManager } from '../../../service/authorizationmanager';
import { PromotionService } from '../../../service/promotionService';
import { PromotionStatusService } from '../../../service/promotionStatusService';
import { DiscountService } from '../../../service/discountService';
import { DiscountTypeService } from '../../../service/discountTypeService';
import { Promotion } from '../../../entity/promotion';
import { Promotionstatus } from '../../../entity/promotionstatus';
import { Discount } from '../../../entity/discount';
import { Discounttype } from '../../../entity/discounttype';
import { MessageComponent } from '../../../util/dialog/message/message.component';
import { ConfirmComponent } from '../../../util/dialog/confirm/confirm.component';

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.css']
})
export class PromotionComponent implements OnInit{
  promotionForm: FormGroup;
  discountForm: FormGroup;
  searchForm: FormGroup;

  promotions: Promotion[] = [];
  discounts: Discount[] = [];
  promotionStatuses: Promotionstatus[] = [];
  discountTypes: Discounttype[] = [];
  promotionData = new MatTableDataSource<Promotion>([]);
  discountData = new MatTableDataSource<Discount>([]);

  promotionColumns = ['title', 'startdate', 'enddate', 'status'];
  discountColumns = ['type', 'discountvalue', 'maximumdiscount', 'actions'];
  selectedPromotion: Promotion | null = null;
  oldPromotion: Promotion | null = null;
  selectedDiscount: Discount | null = null;
  imageurl = 'assets/pending.gif';

  enaadd = true;
  enaupd = false;
  enadel = false;
  hasInsertAuthority = false;
  hasUpdateAuthority = false;
  hasDeleteAuthority = false;

  @ViewChild('promotionPaginator') promotionPaginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private promotionService: PromotionService,
    private promotionStatusService: PromotionStatusService,
    private discountService: DiscountService,
    private discountTypeService: DiscountTypeService,
    public authService: AuthorizationManager
  ) {
    this.promotionForm = this.fb.group({
      title: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
      startdate: new FormControl('', Validators.required),
      enddate: new FormControl('', Validators.required),
      promotionstatus: new FormControl(null, Validators.required)
    }, { validators: this.dateRangeValidator });

    this.discountForm = this.fb.group({
      discountvalue: new FormControl(null, [Validators.required, Validators.min(0.01)]),
      maximumdiscount: new FormControl(null, Validators.min(0)),
      discounttype: new FormControl(null, Validators.required)
    });

    this.searchForm = this.fb.group({
      title: new FormControl(''),
      startdate: new FormControl('')
    });
  }

  async ngOnInit(): Promise<void> {
    this.setAuthorities();
    await Promise.all([this.loadLookups(), this.loadPromotions()]);
    this.discountForm.get('discounttype')?.valueChanges.subscribe(type => this.applyDiscountRules(type));
  }

  private dateRangeValidator(group: AbstractControl): ValidationErrors | null {
    const start = group.get('startdate')?.value;
    const end = group.get('enddate')?.value;
    return start && end && end < start ? { invalidDateRange: true } : null;
  }

  private setAuthorities(): void {
    const source = this.authService.getAuthorities();
    if (!Array.isArray(source)) return;
    const authorities = this.authService.extractAuthorities(source);
    this.hasInsertAuthority = authorities.some(a => a.module === 'promotion' && a.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(a => a.module === 'promotion' && a.operation === 'update');
    this.hasDeleteAuthority = authorities.some(a => a.module === 'promotion' && a.operation === 'delete');
  }

  private async loadLookups(): Promise<void> {
    [this.promotionStatuses, this.discountTypes] = await Promise.all([
      this.promotionStatusService.getAllList(),
      this.discountTypeService.getAllList()
    ]);
  }

  async loadPromotions(query = ''): Promise<void> {
    try {
      this.promotions = await this.promotionService.getAll(query);
      this.promotionData.data = this.promotions;
      this.promotionData.paginator = this.promotionPaginator;
      this.imageurl = 'assets/fullfilled.png';
    } catch (error) {
      this.imageurl = 'assets/rejected.png';
      this.showMessage('Promotion Load Failed', this.errorText(error));
    }
  }

  async search(): Promise<void> {
    const { title, startdate } = this.searchForm.getRawValue();
    const params: string[] = [];
    if (title?.trim()) params.push(`title=${encodeURIComponent(title.trim())}`);
    if (startdate) params.push(`startdate=${encodeURIComponent(startdate)}`);
    await this.loadPromotions(params.length ? `?${params.join('&')}` : '');
  }

  async clearSearch(): Promise<void> {
    this.searchForm.reset();
    await this.loadPromotions();
  }

  async addPromotion(): Promise<void> {
    if (!this.validate(this.promotionForm)) return;
    const value = this.promotionForm.getRawValue();
    const promotion = { ...value, promotionstatus: { id: value.promotionstatus.id } } as Promotion;
    if (!await this.confirm('Confirmation - Promotion Add', `Add promotion <b>${promotion.title}</b>?`)) return;
    try {
      const response = await this.promotionService.add(promotion);
      if (response?.errors) return this.showMessage('Promotion Add Failed', response.errors);
      this.showMessage('Status - Promotion Add', 'Successfully Saved');
      this.clearPromotionForm();
      await this.loadPromotions();
    } catch (error) { this.showMessage('Promotion Add Failed', this.errorText(error)); }
  }

  fillPromotion(promotion: Promotion): void {
    this.selectedPromotion = JSON.parse(JSON.stringify(promotion));
    this.oldPromotion = JSON.parse(JSON.stringify(promotion));
    const status = this.promotionStatuses.find(x => x.id === promotion.promotionstatus.id) ?? null;
    this.promotionForm.patchValue({ ...promotion, promotionstatus: status });
    this.promotionForm.markAsPristine();
    this.enaadd = false; this.enaupd = true; this.enadel = true;
    this.loadDiscountsForPromotion(promotion.id);
  }

  async updatePromotion(): Promise<void> {
    if (!this.selectedPromotion || !this.oldPromotion || !this.validate(this.promotionForm)) return;
    if (this.promotionForm.pristine) return this.showMessage('Promotion Update', 'Nothing Changed');
    if (!await this.confirm('Confirmation - Promotion Update', 'Save the changed Promotion details?')) return;
    const value = this.promotionForm.getRawValue();
    const promotion = { ...value, id: this.oldPromotion.id,
      promotionstatus: { id: value.promotionstatus.id } } as Promotion;
    try {
      const response = await this.promotionService.update(promotion);
      if (response?.errors) return this.showMessage('Promotion Update Failed', response.errors);
      this.showMessage('Status - Promotion Update', 'Successfully Updated');
      this.clearPromotionForm();
      await this.loadPromotions();
    } catch (error) { this.showMessage('Promotion Update Failed', this.errorText(error)); }
  }

  async deletePromotion(): Promise<void> {
    if (!this.selectedPromotion) return;
    if (!await this.confirm('Confirmation - Promotion Delete',
      `Delete <b>${this.selectedPromotion.title}</b>? Associated discounts may prevent deletion.`)) return;
    try {
      const response = await this.promotionService.delete(this.selectedPromotion.id);
      if (response?.errors) return this.showMessage('Promotion Delete Failed', response.errors);
      this.showMessage('Status - Promotion Delete', 'Successfully Deleted');
      this.clearPromotionForm();
      await this.loadPromotions();
    } catch (error) { this.showMessage('Promotion Delete Failed', this.errorText(error)); }
  }

  clearPromotionForm(): void {
    this.promotionForm.reset();
    this.promotionForm.markAsPristine();
    this.promotionForm.markAsUntouched();
    this.discountForm.reset();
    this.discountData.data = [];
    this.selectedPromotion = this.oldPromotion = null;
    this.selectedDiscount = null;
    this.enaadd = true; this.enaupd = false; this.enadel = false;
  }

  private async loadDiscountsForPromotion(promotionId: number): Promise<void> {
    const all = await this.discountService.getAll();
    this.discounts = all.filter(d => d.promotion?.id === promotionId);
    this.discountData.data = this.discounts;
  }

  private applyDiscountRules(type: Discounttype | null): void {
    const value = this.discountForm.get('discountvalue');
    const max = this.discountForm.get('maximumdiscount');
    value?.setValidators([Validators.required, Validators.min(0.01)]);
    if (type?.name?.toUpperCase() === 'PERCENTAGE') {
      value?.addValidators(Validators.max(100));
      max?.enable({ emitEvent: false });
    } else if (type) {
      max?.reset(null, { emitEvent: false });
      max?.disable({ emitEvent: false });
    }
    value?.updateValueAndValidity({ emitEvent: false });
  }

  async addDiscount(): Promise<void> {
    if (!this.selectedPromotion) return this.showMessage('Discount', 'Save or select a Promotion first');
    if (!this.validate(this.discountForm)) return;
    const value = this.discountForm.getRawValue();
    const discount = { discountvalue: value.discountvalue, maximumdiscount: value.maximumdiscount,
      discounttype: { id: value.discounttype.id },
      promotion: { id: this.selectedPromotion.id } } as Discount;
    if (!await this.confirm('Confirmation - Discount Add', 'Add this Discount to the selected Promotion?')) return;
    try {
      const response = await this.discountService.add(discount);
      if (response?.errors) return this.showMessage('Discount Add Failed', response.errors);
      this.showMessage('Status - Discount Add', 'Successfully Saved');
      this.discountForm.reset();
      await this.loadDiscountsForPromotion(this.selectedPromotion.id);
    } catch (error) { this.showMessage('Discount Add Failed', this.errorText(error)); }
  }

  editDiscount(discount: Discount): void {
    this.selectedDiscount = discount;
    const type = this.discountTypes.find(x => x.id === discount.discounttype.id) ?? null;
    this.discountForm.patchValue({ discountvalue: discount.discountvalue, maximumdiscount: discount.maximumdiscount, discounttype: type });
    this.discountForm.markAsPristine();
  }

  async updateDiscount(): Promise<void> {
    if (!this.selectedPromotion || !this.selectedDiscount || !this.validate(this.discountForm)) return;
    const value = this.discountForm.getRawValue();
    const discount = { id: this.selectedDiscount.id, discountvalue: value.discountvalue, maximumdiscount: value.maximumdiscount,
      discounttype: { id: value.discounttype.id }, promotion: { id: this.selectedPromotion.id } } as Discount;
    try {
      const response = await this.discountService.update(discount);
      if (response?.errors) return this.showMessage('Discount Update Failed', response.errors);
      this.showMessage('Status - Discount Update', 'Successfully Updated');
      this.discountForm.reset(); this.selectedDiscount = null;
      await this.loadDiscountsForPromotion(this.selectedPromotion.id);
    } catch (error) { this.showMessage('Discount Update Failed', this.errorText(error)); }
  }

  async deleteDiscount(discount: Discount): Promise<void> {
    if (!await this.confirm('Confirmation - Discount Delete', 'Delete this Discount?')) return;
    try {
      const response = await this.discountService.delete(discount.id);
      if (response?.errors) return this.showMessage('Discount Delete Failed', response.errors);
      this.showMessage('Status - Discount Delete', 'Successfully Deleted');
      if (this.selectedPromotion) await this.loadDiscountsForPromotion(this.selectedPromotion.id);
    } catch (error) { this.showMessage('Discount Delete Failed', this.errorText(error)); }
  }

  private validate(form: FormGroup): boolean {
    if (form.valid) return true;
    form.markAllAsTouched();
    this.showMessage('Validation Errors', 'Please correct the highlighted fields');
    return false;
  }

  private async confirm(heading: string, message: string): Promise<boolean> {
    return !!await this.dialog.open(ConfirmComponent, { width: '500px', data: { heading, message } })
      .afterClosed().toPromise();
  }

  private showMessage(heading: string, message: string): void {
    this.dialog.open(MessageComponent, { width: '500px', data: { heading, message } });
  }

  private errorText(error: any): string {
    return error?.error?.message || error?.message || 'Server not found';
  }
}
