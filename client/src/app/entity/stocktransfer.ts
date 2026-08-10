export interface StockTransferLookup {
  id: number;
  name: string;
}

export interface AvailableStockItem {
  itemId: number;
  itemnumber: string;
  name: string;
  availableQuantity: number;
  unitType: string;
}

export interface StockTransferItemRequest {
  itemId: number;
  quantity: number;
}

export interface StockTransferRequest {
  fromLocationId: number;
  toLocationId: number;
  employeeId: number;
  note: string;
  items: StockTransferItemRequest[];
}

export interface StockTransferItemResponse {
  id: number;
  itemId: number;
  itemnumber: string;
  itemName: string;
  quantity: number;
}

export interface StockTransfer {
  id: number;
  transferdate: string;
  note: string;
  fromLocationId: number;
  fromLocationName: string;
  toLocationId: number;
  toLocationName: string;
  employeeId: number;
  employeeName: string;
  createdByUsername: string;
  items: StockTransferItemResponse[];
}

export interface StockTransferCreateResponse {
  id: string;
  message: string;
}

export interface TransferTableItem extends AvailableStockItem {
  quantity: number;
}
