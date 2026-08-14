import {Component, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {DatePipe} from '@angular/common';
import {Expense} from '../../../entity/expense';
import {Expensecategory} from '../../../entity/expensecategory';
import {Paymentmethod} from '../../../entity/paymentmethod';
import {ExpenseService} from '../../../service/expenseservice';
import {ExpensecategoryService} from '../../../service/expensecategoryservice';
import {PaymentmethodService} from '../../../service/paymentmethodservice';
import {AuthorizationManager} from '../../../service/authorizationmanager';
import {MessageComponent} from '../../../util/dialog/message/message.component';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';

@Component({
  selector: 'app-expense',
  templateUrl: './expense.component.html',
  styleUrls: ['./expense.component.css']
})
export class ExpenseComponent {
  columns = ['expenseNumber', 'paymentDate', 'category', 'paymentmethod', 'amount', 'paidByUsername'];
  data = new MatTableDataSource<Expense>([]);
  expenses: Expense[] = [];
  categories: Expensecategory[] = [];
  paymentmethods: Paymentmethod[] = [];
  selectedrow?: Expense;
  oldexpense?: Expense;
  imageurl = '';
  filteredTotal = 0;

  form: FormGroup;
  ssearch: FormGroup;
  enaadd = true;
  enaupd = false;
  enadel = false;
  hasInsertAuthority = false;
  hasUpdateAuthority = false;
  hasDeleteAuthority = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private fb: FormBuilder,
              private expenseService: ExpenseService,
              private categoryService: ExpensecategoryService,
              private paymentmethodService: PaymentmethodService,
              private dialog: MatDialog,
              private datepipe: DatePipe,
              public authService: AuthorizationManager) {
    const today = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
    this.form = this.fb.group({
      expenseNumber: new FormControl({value: '', disabled: true}),
      paymentDate: new FormControl(today, Validators.required),
      expensecategory: new FormControl(null, Validators.required),
      amount: new FormControl(null, [Validators.required, Validators.min(0.01)]),
      paymentmethod: new FormControl(null, Validators.required),
      description: new FormControl('', [Validators.required, Validators.maxLength(255)])
    });
    this.ssearch = this.fb.group({
      number: new FormControl(''), category: new FormControl(null),
      from: new FormControl(null), to: new FormControl(null)
    });
  }

  ngOnInit(): void {
    const authoritiesArray = this.authService.getAuthorities();
    if (Array.isArray(authoritiesArray)) this.buttonStates(this.authService.extractAuthorities(authoritiesArray));
    Promise.all([this.categoryService.getAllList(), this.paymentmethodService.getAllList()])
      .then(([categories, methods]) => { this.categories = categories; this.paymentmethods = methods; });
    this.loadTable('');
  }

  buttonStates(authorities: {module: string; operation: string}[]): void {
    this.hasInsertAuthority = authorities.some(a => a.module === 'expense' && a.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(a => a.module === 'expense' && a.operation === 'update');
    this.hasDeleteAuthority = authorities.some(a => a.module === 'expense' && a.operation === 'delete');
  }

  loadTable(query: string): void {
    this.imageurl = 'assets/pending.gif';
    this.expenseService.getAll(query).then(items => {
      this.expenses = items;
      this.data = new MatTableDataSource(items);
      this.data.paginator = this.paginator;
      this.filteredTotal = items.reduce((sum, item) => sum + Number(item.amount || 0), 0);
      this.imageurl = 'assets/fullfilled.png';
    }).catch(() => this.imageurl = 'assets/rejected.png');
  }

  search(): void {
    const value = this.ssearch.getRawValue();
    const params: string[] = [];
    if (value.number && value.number.trim()) params.push('number=' + encodeURIComponent(value.number.trim()));
    if (value.category) params.push('category=' + encodeURIComponent(value.category.name));
    if (value.from) params.push('from=' + this.datepipe.transform(value.from, 'yyyy-MM-dd'));
    if (value.to) params.push('to=' + this.datepipe.transform(value.to, 'yyyy-MM-dd'));
    this.loadTable(params.length ? '?' + params.join('&') : '');
  }

  clearSearch(): void {
    const ref = this.dialog.open(ConfirmComponent, {width: '500px', data: {heading: 'Search Clear', message: 'Are you sure to clear the search?'}});
    ref.afterClosed().subscribe(result => { if (result) { this.ssearch.reset(); this.loadTable(''); } });
  }

  getErrors(): string {
    if (this.form.valid) return '';
    const labels: {[key: string]: string} = {paymentDate: 'Payment Date', expensecategory: 'Expense Category', amount: 'Amount', paymentmethod: 'Payment Method', description: 'Description'};
    return Object.keys(this.form.controls).filter(k => this.form.controls[k].errors).map(k => '<br>Invalid ' + (labels[k] || k)).join('');
  }

  add(): void {
    const errors = this.getErrors();
    if (errors) { this.message('Errors - Expense Add', 'You have following errors <br>' + errors); return; }
    const expense = this.form.getRawValue() as Expense;
    const ref = this.dialog.open(ConfirmComponent, {width: '500px', data: {heading: 'Confirmation - Expense Add', message: `Are you sure to add this expense?<br><br>Amount: Rs. ${Number(expense.amount).toFixed(2)}`} });
    ref.afterClosed().subscribe(result => {
      if (!result) return;
      this.expenseService.add(expense).then(response => {
        if (response && response.errors === '') { this.message('Status - Expense Add', 'Successfully Saved'); this.resetForm(); this.loadTable(''); }
        else this.message('Status - Expense Add', response?.errors || 'Server Not Found');
      }).catch(() => this.message('Status - Expense Add', 'Server Not Found'));
    });
  }

  fillForm(row: Expense): void {
    this.selectedrow = row;
    this.oldexpense = JSON.parse(JSON.stringify(row));
    const copy: Expense = JSON.parse(JSON.stringify(row));
    copy.expensecategory = this.categories.find(c => c.id === row.expensecategory.id) || row.expensecategory;
    copy.paymentmethod = this.paymentmethods.find(m => m.id === row.paymentmethod.id) || row.paymentmethod;
    this.form.patchValue(copy);
    this.form.markAsPristine();
    this.enaadd = false; this.enaupd = true; this.enadel = true;
  }

  update(): void {
    const errors = this.getErrors();
    if (errors) { this.message('Errors - Expense Update', 'You have following errors <br>' + errors); return; }
    if (!this.form.dirty) { this.message('Confirmation - Expense Update', 'Nothing Changed'); return; }
    const updated = this.form.getRawValue() as Expense;
    updated.id = this.oldexpense!.id;
    const ref = this.dialog.open(ConfirmComponent, {width: '500px', data: {heading: 'Confirmation - Expense Update', message: 'Are you sure to save the changes?'}});
    ref.afterClosed().subscribe(result => { if (result) this.expenseService.update(updated).then(response => {
      if (response && response.errors === '') { this.message('Status - Expense Update', 'Successfully Updated'); this.resetForm(); this.loadTable(''); }
      else this.message('Status - Expense Update', response?.errors || 'Server Not Found');
    }).catch(() => this.message('Status - Expense Update', 'Server Not Found')); });
  }

  delete(): void {
    if (!this.selectedrow) return;
    const ref = this.dialog.open(ConfirmComponent, {width: '500px', data: {heading: 'Confirmation - Expense Delete', message: `Are you sure to delete ${this.selectedrow.expenseNumber}?`}});
    ref.afterClosed().subscribe(result => { if (result) this.expenseService.delete(this.selectedrow!.id).then(response => {
      if (response && response.errors === '') { this.message('Status - Expense Delete', 'Successfully Deleted'); this.resetForm(); this.loadTable(''); }
      else this.message('Status - Expense Delete', response?.errors || 'Server Not Found');
    }).catch(() => this.message('Status - Expense Delete', 'Server Not Found')); });
  }

  clear(): void {
    const ref = this.dialog.open(ConfirmComponent, {width: '500px', data: {heading: 'Confirmation - Expense Clear', message: 'Are you sure to clear the entered details?'}});
    ref.afterClosed().subscribe(result => { if (result) this.resetForm(); });
  }

  private resetForm(): void {
    this.form.reset({expenseNumber: '', paymentDate: this.datepipe.transform(new Date(), 'yyyy-MM-dd'), expensecategory: null, amount: null, paymentmethod: null, description: ''});
    this.selectedrow = undefined; this.oldexpense = undefined;
    this.enaadd = true; this.enaupd = false; this.enadel = false;
  }

  private message(heading: string, message: string): void {
    this.dialog.open(MessageComponent, {width: '500px', data: {heading, message}});
  }
}
