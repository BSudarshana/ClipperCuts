import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {
  StockWriteOff,
  StockWriteOffAvailableItem,
  StockWriteOffLookup,
  StockWriteOffRequest,
  StockWriteOffTableItem
} from '../../../entity/stockwriteoff';
import { AuthorizationManager } from '../../../service/authorizationmanager';
import { StockWriteOffService } from '../../../service/stockwriteoffservice';
import { ConfirmComponent } from '../../../util/dialog/confirm/confirm.component';
import { MessageComponent } from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-stockwriteoff',
  templateUrl: './stockwriteoff.component.html',
  styleUrls: ['./stockwriteoff.component.css']
})
export class StockwriteoffComponent implements OnInit {
  writeOffForm: FormGroup;
  itemForm: FormGroup;
  searchForm: FormGroup;

  readonly reasons = [
    'Used for Services',
    'Finished/Empty',
    'Damaged',
    'Expired',
    'Missing/Stock Loss',
    'Other'
  ];

  locations: StockWriteOffLookup[] = [];
  availableItems: StockWriteOffAvailableItem[] = [];
  writeOffItems: StockWriteOffTableItem[] = [];
  writeOffs: StockWriteOff[] = [];

  historyData = new MatTableDataSource<StockWriteOff>([]);
  selectedWriteOff: StockWriteOff | null = null;
  selectedHistoryRow: StockWriteOff | null = null;

  itemColumns = ['itemnumber', 'name', 'availableQuantity', 'quantity', 'unitType', 'actions'];

  historyColumns = [
    'writeoffnumber',
    'writeoffdate',
    'locationName',
    'reason',
    'totalQuantity',
    'createdByUsername',
    'actions'
  ];

  detailColumns = ['itemnumber', 'itemName', 'quantity', 'unitType'];

  loadingLocations = false;
  loadingItems = false;
  loadingHistory = false;
  loadingDetail = false;
  saving = false;
  hasInsertAuthority = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private writeOffService: StockWriteOffService,
    private dialog: MatDialog,
    public authService: AuthorizationManager
  ) {
    this.writeOffForm = this.fb.group({
      locationId: [null, Validators.required],
      reason: [null, Validators.required],
      note: ['', Validators.maxLength(255)]
    });

    this.itemForm = this.fb.group({
      item: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.min(0.01)]]
    });

    this.searchForm = this.fb.group({
      searchText: ['']
    });
  }

  ngOnInit(): void {
    this.initializeAuthority();
    this.loadLocations();
    this.loadHistory();
  }

  get selectedAvailableItem(): StockWriteOffAvailableItem | null {
    return this.itemForm.get('item')?.value || null;
  }

  get totalWriteOffQuantity(): number {
    return this.writeOffItems.reduce((total, item) => total + Number(item.quantity), 0);
  }

  private initializeAuthority(): void {
    const authoritiesArray = this.authService.getAuthorities();
    if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
      const authorities = this.authService.extractAuthorities(authoritiesArray);
      this.hasInsertAuthority = authorities.some(
        (authority: { module: string; operation: string }) =>
          authority.module === 'stockwriteoff' && authority.operation === 'insert'
      );
    }
  }

  private loadLocations(): void {
    this.loadingLocations = true;
    this.writeOffService.getLocations().subscribe({
      next: (locations) => {
        this.locations = locations || [];
        this.loadingLocations = false;
      },
      error: (error) => {
        this.loadingLocations = false;
        this.handleError(error, 'Unable to load inventory locations.');
      }
    });
  }

  onLocationChange(): void {
    const locationId = Number(this.writeOffForm.get('locationId')?.value);
    this.availableItems = [];
    this.writeOffItems = [];
    this.itemForm.reset();

    if (!locationId) {
      return;
    }

    this.loadingItems = true;
    this.writeOffService.getAvailableItems(locationId).subscribe({
      next: (items) => {
        this.availableItems = (items || []).filter((item) => Number(item.availableQuantity) > 0);
        this.loadingItems = false;
      },
      error: (error) => {
        this.loadingItems = false;
        this.handleError(error, 'Unable to load products for the selected location.');
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

    if (this.writeOffItems.some((row) => row.itemId === item.itemId)) {
      this.showMessage(
        'Errors - Stock Write-Off Product Add',
        `${item.name} has already been added.`
      );
      return;
    }

    if (quantity > Number(item.availableQuantity)) {
      this.itemForm.get('quantity')?.setErrors({ exceedsAvailable: true });
      this.showMessage(
        'Errors - Stock Write-Off Product Add',
        `Write-off quantity cannot exceed ${item.availableQuantity} ${item.unitType}.`
      );
      return;
    }

    this.writeOffItems = [...this.writeOffItems, { ...item, quantity }];

    this.itemForm.reset();
  }

  removeItem(itemId: number): void {
    const item = this.writeOffItems.find((value) => value.itemId === itemId);
    if (!item) {
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Remove Product',
        message: `Are you sure to remove the following product?<br><br>${item.itemnumber} - ${item.name}`
      }
    });

    confirmation.afterClosed().subscribe((result) => {
      if (result) {
        this.writeOffItems = this.writeOffItems.filter((value) => value.itemId !== itemId);
      }
    });
  }

  submitWriteOff(): void {
    this.writeOffForm.markAllAsTouched();

    if (this.writeOffForm.invalid) {
      this.showMessage(
        'Errors - Stock Write-Off',
        'Complete the required location and reason fields.'
      );
      return;
    }

    if (this.writeOffItems.length === 0) {
      this.showMessage(
        'Errors - Stock Write-Off',
        'Add at least one product to the stock write-off.'
      );
      return;
    }

    const locationName = this.lookupLocationName(this.writeOffForm.get('locationId')?.value);

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Stock Write-Off',
        message:
          `Are you sure to write off ${this.totalWriteOffQuantity} unit(s)?<br><br>` +
          `Location: ${locationName}<br>` +
          `Reason: ${this.writeOffForm.get('reason')?.value}`
      }
    });

    confirmation.afterClosed().subscribe((result) => {
      if (result) {
        this.saveWriteOff();
      }
    });
  }

  private saveWriteOff(): void {
    const value = this.writeOffForm.getRawValue();
    const request: StockWriteOffRequest = {
      locationId: Number(value.locationId),
      reason: value.reason,
      note: value.note?.trim() || null,
      items: this.writeOffItems.map((item) => ({
        itemId: item.itemId,
        quantity: Number(item.quantity)
      }))
    };

    this.saving = true;
    this.writeOffService.create(request).subscribe({
      next: (response) => {
        this.saving = false;
        this.resetWriteOffForm();
        this.loadHistory();
        this.showMessage(
          'Status - Stock Write-Off',
          `${response.writeoffnumber} completed successfully.`
        );
      },
      error: (error) => {
        this.saving = false;
        this.handleError(error, 'Stock write-off could not be completed.');
      }
    });
  }

  clearWriteOff(): void {
    const hasData = this.writeOffForm.dirty || this.writeOffItems.length > 0;
    if (!hasData) {
      this.resetWriteOffForm();
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Stock Write-Off Clear',
        message: 'Are you sure to clear the entered stock write-off details?'
      }
    });

    confirmation.afterClosed().subscribe((result) => {
      if (result) {
        this.resetWriteOffForm();
      }
    });
  }

  private resetWriteOffForm(): void {
    this.writeOffForm.reset({
      locationId: null,
      reason: null,
      note: ''
    });
    this.itemForm.reset();
    this.availableItems = [];
    this.writeOffItems = [];
  }

  loadHistory(): void {
    this.loadingHistory = true;
    this.writeOffService.getAll().subscribe({
      next: (writeOffs) => {
        this.writeOffs = writeOffs || [];
        this.setHistoryData(this.writeOffs);
        this.loadingHistory = false;
      },
      error: (error) => {
        this.loadingHistory = false;
        this.handleError(error, 'Unable to load stock write-off history.');
      }
    });
  }

  search(): void {
    const searchText = String(this.searchForm.get('searchText')?.value || '')
      .trim()
      .toLowerCase();

    if (!searchText) {
      this.setHistoryData(this.writeOffs);
      return;
    }

    const filtered = this.writeOffs.filter(
      (writeOff) =>
        writeOff.writeoffnumber.toLowerCase().includes(searchText) ||
        writeOff.locationName.toLowerCase().includes(searchText) ||
        writeOff.reason.toLowerCase().includes(searchText) ||
        writeOff.createdByUsername.toLowerCase().includes(searchText)
    );

    this.setHistoryData(filtered);
  }

  clearSearch(): void {
    this.searchForm.reset({ searchText: '' });
    this.setHistoryData(this.writeOffs);
  }

  viewWriteOff(id: number): void {
    this.selectedHistoryRow = this.writeOffs.find((writeOff) => writeOff.id === id) || null;
    this.selectedWriteOff = null;
    this.loadingDetail = true;

    this.writeOffService.getById(id).subscribe({
      next: (writeOff) => {
        this.selectedWriteOff = writeOff;
        this.loadingDetail = false;
      },
      error: (error) => {
        this.loadingDetail = false;
        this.handleError(error, 'Unable to load stock write-off details.');
      }
    });
  }

  closeDetail(): void {
    this.selectedWriteOff = null;
    this.selectedHistoryRow = null;
  }

  private setHistoryData(writeOffs: StockWriteOff[]): void {
    this.historyData = new MatTableDataSource(writeOffs);
    this.historyData.paginator = this.paginator;
  }

  private lookupLocationName(id: number): string {
    return this.locations.find((location) => location.id === Number(id))?.name || '';
  }

  private handleError(error: any, fallback: string): void {
    const message = error?.error?.message || error?.error?.error || error?.message || fallback;

    this.showMessage('Stock Write-Off - Error', message);
  }

  private showMessage(heading: string, message: string): void {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: { heading, message }
    });
  }
}
