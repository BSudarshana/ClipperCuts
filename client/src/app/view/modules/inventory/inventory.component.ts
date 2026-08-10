import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';

import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {InventoryLocation, InventoryRecord} from '../../../entity/inventory';
import {InventorySearch, InventoryService} from '../../../service/inventoryservice';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit, AfterViewInit {
  columns: string[] = [
    'itemnumber', 'itemName', 'brand', 'locationName', 'locationType',
    'quantity', 'unitType', 'rop', 'totalStock', 'lastupdate', 'status'
  ];

  searchForm: FormGroup;
  data = new MatTableDataSource<InventoryRecord>([]);
  inventory: InventoryRecord[] = [];
  locations: InventoryLocation[] = [];

  loading = false;
  loadError = '';
  stockRecordCount = 0;
  productCount = 0;
  lowStockProductCount = 0;
  locationCount = 0;

  @ViewChild
  (MatPaginator) paginator!: MatPaginator;

  constructor(
    private inventoryService: InventoryService,
    private formBuilder: FormBuilder
  ) {
    this.searchForm = this.formBuilder.group({
      itemnumber: new FormControl(''),
      name: new FormControl(''),
      locationId: new FormControl(null),
      stockState: new FormControl('ALL')
    });
  }

  ngOnInit(): void {
    this.loadInitialData();
  }

  ngAfterViewInit(): void {
    this.data.paginator = this.paginator;
  }

  private async loadInitialData(): Promise<void> {
    try {
      this.loading = true;
      this.loadError = '';
      const [records, locations] = await Promise.all([
        this.inventoryService.getAll(),
        this.inventoryService.getLocations()
      ]);
      this.locations = locations;
      this.setInventory(records);
    } catch (error: any) {
      console.error(error);
      this.loadError = error?.error?.message || error?.message || 'Unable to load inventory.';
      this.setInventory([]);
    } finally {
      this.loading = false;
    }
  }

  async search(): Promise<void> {
    const form = this.searchForm.getRawValue();
    const criteria: InventorySearch = {
      itemnumber: form.itemnumber,
      name: form.name,
      locationId: form.locationId ?? undefined
    };

    if (form.stockState === 'LOW') criteria.lowStock = true;
    if (form.stockState === 'OK') criteria.lowStock = false;

    try {
      this.loading = true;
      this.loadError = '';
      this.setInventory(await this.inventoryService.getAll(criteria));
    } catch (error: any) {
      console.error(error);
      this.loadError = error?.error?.message || error?.message || 'Unable to search inventory.';
      this.setInventory([]);
    } finally {
      this.loading = false;
    }
  }

  clearSearch(): void {
    this.searchForm.reset({itemnumber: '', name: '', locationId: null, stockState: 'ALL'});
    this.loadInitialData();
  }

  private setInventory(records: InventoryRecord[]): void {
    this.inventory = records;
    this.data.data = records;
    this.stockRecordCount = records.length;
    this.productCount = new Set(records.map(row => row.itemId)).size;
    this.lowStockProductCount = new Set(records.filter(row => row.lowStock).map(row => row.itemId)).size;
    this.locationCount = new Set(records.filter(row => row.locationId != null).map(row => row.locationId)).size;
    if (this.data.paginator) this.data.paginator.firstPage();
  }
}
