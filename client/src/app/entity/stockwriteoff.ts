export interface StockWriteOffLookup {
  id: number;
  name: string;
}

export interface StockWriteOffAvailableItem {
  itemId: number;
  itemnumber: string;
  name: string;
  availableQuantity: number;
  unitType: string;
}

export interface StockWriteOffTableItem extends StockWriteOffAvailableItem {
  quantity: number;
}

export interface StockWriteOffItemRequest {
  itemId: number;
  quantity: number;
}

export interface StockWriteOffRequest {
  locationId: number;
  reason: string;
  note: string | null;
  items: StockWriteOffItemRequest[];
}

export interface StockWriteOffItemResponse {
  id: number;
  itemId: number;
  itemnumber: string;
  itemName: string;
  unitType: string;
  quantity: number;
}

export interface StockWriteOff {
  id: number;
  writeoffnumber: string;
  writeoffdate: string;
  reason: string;
  note?: string | null;
  locationId: number;
  locationName: string;
  createdByUsername: string;
  totalQuantity: number;
  items: StockWriteOffItemResponse[];
}

export interface StockWriteOffCreateResponse {
  id: string;
  writeoffnumber: string;
  message: string;
}
