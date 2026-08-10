import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {forkJoin} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {
  AvailableStockItem,
  StockTransfer,
  StockTransferLookup,
  StockTransferRequest,
  TransferTableItem
} from '../../../entity/stocktransfer';
import {StockTransferService} from '../../../service/stocktransferservice';
import {AuthorizationManager} from '../../../service/authorizationmanager';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-stocktransfer',
  templateUrl: './stocktransfer.component.html',
  styleUrls: ['./stocktransfer.component.css']
})
export class StocktransferComponent implements OnInit {
  transferForm: FormGroup;
  itemForm: FormGroup;

  locations: StockTransferLookup[] = [];
  employees: StockTransferLookup[] = [];
  availableItems: AvailableStockItem[] = [];
  transferItems: TransferTableItem[] = [];
  transfers: StockTransfer[] = [];
  historyData = new MatTableDataSource<StockTransfer>([]);
  selectedTransfer: StockTransfer | null = null;
  selectedHistoryRow: StockTransfer | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  itemColumns = ['itemnumber', 'name', 'availableQuantity', 'quantity', 'unitType', 'actions'];
  historyColumns = ['id', 'transferdate', 'from', 'to', 'employee', 'createdBy', 'actions'];
  detailColumns = ['itemnumber', 'itemName', 'quantity'];

  loadingLookups = false;
  loadingItems = false;
  loadingHistory = false;
  loadingDetail = false;
  saving = false;
  errorMessage = '';
  hasInsertAuthority = false;

  constructor(
    private fb: FormBuilder,
    private stockTransferService: StockTransferService,
    private matdialog: MatDialog,
    public authService: AuthorizationManager
  ) {
    this.transferForm = this.fb.group({
      fromLocationId: [null, Validators.required],
      toLocationId: [null, Validators.required],
      employeeId: [null, Validators.required],
      note: ['', [Validators.maxLength(500)]]
    }, {validators: this.differentLocationsValidator});

    this.itemForm = this.fb.group({
      item: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.initializeAuthority();
    this.loadInitialData();
  }

  private initializeAuthority(): void {
    const authoritiesArray = this.authService.getAuthorities();
    if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
      const authorities = this.authService.extractAuthorities(authoritiesArray);
      this.hasInsertAuthority = authorities.some(
        (authority: {module: string; operation: string}) =>
          authority.module === 'stocktransfer' && authority.operation === 'insert'
      );
    }
  }

  get selectedAvailableItem(): AvailableStockItem | null {
    return this.itemForm.get('item')?.value || null;
  }

  get destinationLocations(): StockTransferLookup[] {
    const sourceId = Number(this.transferForm.get('fromLocationId')?.value);
    return this.locations.filter(location => location.id !== sourceId);
  }

  private differentLocationsValidator(control: AbstractControl): ValidationErrors | null {
    const fromId = control.get('fromLocationId')?.value;
    const toId = control.get('toLocationId')?.value;
    return fromId && toId && Number(fromId) === Number(toId)
      ? {sameLocation: true}
      : null;
  }

  private loadInitialData(): void {
    this.loadingLookups = true;
    this.errorMessage = '';

    forkJoin({
      locations: this.stockTransferService.getLocations(),
      employees: this.stockTransferService.getEmployees()
    }).subscribe({
      next: result => {
        this.locations = result.locations || [];
        this.employees = result.employees || [];
        this.loadingLookups = false;
      },
      error: error => {
        this.loadingLookups = false;
        this.handleError(error, 'Unable to load locations and employees.');
      }
    });

    this.loadHistory();
  }

  onSourceLocationChange(): void {
    const sourceId = Number(this.transferForm.get('fromLocationId')?.value);
    const destinationControl = this.transferForm.get('toLocationId');

    if (Number(destinationControl?.value) === sourceId) {
      destinationControl?.setValue(null);
    }

    this.availableItems = [];
    this.transferItems = [];
    this.itemForm.reset();
    this.selectedTransfer = null;

    if (!sourceId) {
      return;
    }

    this.loadingItems = true;
    this.stockTransferService.getAvailableItems(sourceId).subscribe({
      next: items => {
        this.availableItems = (items || []).filter(item => Number(item.availableQuantity) > 0);
        this.loadingItems = false;
      },
      error: error => {
        this.loadingItems = false;
        this.handleError(error, 'Unable to load products for the selected source location.');
      }
    });
  }

  onItemChange(): void {
    this.itemForm.get('quantity')?.reset();
  }

  addItem(): void {
    this.itemForm.markAllAsTouched();
    if (this.itemForm.invalid) {
      return;
    }

    const item = this.selectedAvailableItem;
    const quantity = Number(this.itemForm.get('quantity')?.value);
    if (!item) {
      return;
    }

    if (this.transferItems.some(row => row.itemId === item.itemId)) {
      this.errorMessage = `${item.name} has already been added.`;
      this.showMessage('Errors - Stock Transfer Product Add', this.errorMessage);
      return;
    }

    if (quantity > Number(item.availableQuantity)) {
      this.itemForm.get('quantity')?.setErrors({exceedsAvailable: true});
      this.showMessage(
        'Errors - Stock Transfer Product Add',
        `Transfer quantity cannot exceed the available quantity of ${item.availableQuantity} ${item.unitType}.`
      );
      return;
    }

    this.transferItems = [...this.transferItems, {...item, quantity}];
    this.itemForm.reset();
    this.errorMessage = '';
  }

  removeItem(itemId: number): void {
    const item = this.transferItems.find(value => value.itemId === itemId);
    if (!item) {
      return;
    }

    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Remove Product',
        message: `Are you sure to remove the following product?<br><br>${item.itemnumber} - ${item.name}`
      }
    });

    confirm.afterClosed().subscribe(result => {
      if (result) {
        this.transferItems = this.transferItems.filter(value => value.itemId !== itemId);
      }
    });
  }

  submitTransfer(): void {
    this.transferForm.markAllAsTouched();
    if (this.transferForm.invalid) {
      this.errorMessage = 'Please complete the required transfer details.';
      this.showMessage('Errors - Stock Transfer', this.errorMessage);
      return;
    }

    if (this.transferItems.length === 0) {
      this.errorMessage = 'Add at least one product to the transfer.';
      this.showMessage('Errors - Stock Transfer', this.errorMessage);
      return;
    }

    const sourceName = this.lookupName(this.locations, this.transferForm.value.fromLocationId);
    const destinationName = this.lookupName(this.locations, this.transferForm.value.toLocationId);
    const totalQuantity = this.transferItems.reduce((sum, item) => sum + Number(item.quantity), 0);

    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Stock Transfer',
        message: `Are you sure to transfer ${totalQuantity} unit(s)?<br><br>` +
          `From: ${sourceName}<br>To: ${destinationName}`
      }
    });

    confirm.afterClosed().subscribe(result => {
      if (result) {
        this.saveTransfer();
      }
    });
  }

  private saveTransfer(): void {
    const formValue = this.transferForm.getRawValue();
    const request: StockTransferRequest = {
      fromLocationId: Number(formValue.fromLocationId),
      toLocationId: Number(formValue.toLocationId),
      employeeId: Number(formValue.employeeId),
      note: (formValue.note || '').trim(),
      items: this.transferItems.map(item => ({itemId: item.itemId, quantity: Number(item.quantity)}))
    };

    this.saving = true;
    this.errorMessage = '';
    this.stockTransferService.create(request).subscribe({
      next: response => {
        this.saving = false;
        this.resetTransferForm();
        this.loadHistory();
        this.showMessage('Status - Stock Transfer', response.message || 'Stock transfer completed successfully.');
      },
      error: error => {
        this.saving = false;
        this.handleError(error, 'Stock transfer could not be completed.');
      }
    });
  }

  viewTransfer(id: number): void {
    this.selectedHistoryRow = this.transfers.find(transfer => transfer.id === id) || null;
    this.loadingDetail = true;
    this.selectedTransfer = null;
    this.stockTransferService.getById(id).subscribe({
      next: transfer => {
        this.selectedTransfer = transfer;
        this.loadingDetail = false;
      },
      error: error => {
        this.loadingDetail = false;
        this.handleError(error, 'Unable to load the transfer details.');
      }
    });
  }

  closeDetail(): void {
    this.selectedTransfer = null;
    this.selectedHistoryRow = null;
  }

  clearTransfer(): void {
    const hasData = this.transferForm.dirty || this.transferItems.length > 0;
    if (!hasData) {
      this.resetTransferForm();
      return;
    }

    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Stock Transfer Clear',
        message: 'Are you sure to clear the entered stock transfer details?'
      }
    });

    confirm.afterClosed().subscribe(result => {
      if (result) {
        this.resetTransferForm();
      }
    });
  }

  private resetTransferForm(): void {
    this.transferForm.reset({note: ''});
    this.itemForm.reset();
    this.availableItems = [];
    this.transferItems = [];
    this.errorMessage = '';
  }

  loadHistory(): void {
    this.loadingHistory = true;
    this.stockTransferService.getAll().subscribe({
      next: transfers => {
        this.transfers = transfers || [];
        this.historyData = new MatTableDataSource(this.transfers);
        this.historyData.paginator = this.paginator;
        this.loadingHistory = false;
      },
      error: error => {
        this.loadingHistory = false;
        this.handleError(error, 'Unable to load stock transfer history.');
      }
    });
  }

  lookupName(values: StockTransferLookup[], id: number): string {
    return values.find(value => value.id === Number(id))?.name || '';
  }

  private handleError(error: any, fallback: string): void {
    this.errorMessage = error?.error?.message || error?.error?.error || fallback;
    this.showMessage('Stock Transfer - Error', this.errorMessage);
  }

  private showMessage(heading: string, message: string): void {
    this.matdialog.open(MessageComponent, {
      width: '500px',
      data: {heading, message}
    });
  }
}
