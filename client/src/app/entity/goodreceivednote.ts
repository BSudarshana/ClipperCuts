export interface GrnLookup {
  id: number;
  name: string;
}

export interface GrnPoItem {
  poItemId:number;
  itemId:number;
  itemnumber:string;
  itemName:string;
  unitType:string;
  orderedQuantity:number;
  previouslyReceivedQuantity:number;
  remainingQuantity:number;
  unitCost:number;
}

export interface GrnPurchaseOrder {
  id:number;
  poNumber:string;
  date:string;
  status:string;
  supplierId:number;
  supplierName:string;
  items:GrnPoItem[];
}

export interface GrnReceiptLine extends GrnPoItem {
  receivedQuantity:number;
  subTotal:number;
}

export interface GrnCreateRequest {
  purchaseOrderId:number;
  locationId:number;
  description:string;
  items:{
    poItemId:number;
    receivedQuantity:number
  }[];
}

export interface GrnItemResponse {
  id:number; itemId:number;
  itemnumber:string;
  itemName:string;
  quantity:number;
  unitCost:number;
  subTotal:number;
}

export interface GoodReceivedNote {
  id:number;
  grnNumber:string;
  date:string;
  totalAmount:number;
  description:string;
  status:string;
  purchaseOrderId:number;
  poNumber:string;
  supplierName:string;
  locationId:number;
  locationName:string;
  receivedByUsername:string;
  employeeName:string;
  items:GrnItemResponse[];
}

export interface GrnCreateResponse {
  id:string;
  grnNumber:string;
  message:string;
}
