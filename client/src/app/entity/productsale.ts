export interface SaleLookup {
  id: number;
  name: string;
}
export interface SaleCustomer {
  id: number;
  name: string;
  mobile: string;
}
export interface AvailableSaleItem {
  itemId: number;
  itemnumber: string;
  name: string;
  availableQuantity: number;
  sellingPrice: number;
  unitType: string;
}
export interface SaleLine extends AvailableSaleItem {
  quantity: number;
  subtotal: number;
}
export interface ProductSaleRequest {
  customerId: number | null;
  locationId: number;
  discount: number;
  items: { itemId: number; quantity: number }[];
}
