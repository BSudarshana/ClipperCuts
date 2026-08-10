export interface InventoryLocationType {
  id: number;
  name: string;
}

export interface InventoryLocation {
  id: number;
  name: string;
  description?: string;
  locationtype: InventoryLocationType;
}

export interface InventoryRecord {
  id: number | null;
  itemId: number;
  itemnumber: string;
  itemName: string;
  brand: string | null;
  unitType: string | null;
  category: string | null;
  subcategory: string | null;
  rop: number | null;
  locationId: number | null;
  locationName: string | null;
  locationType: string | null;
  quantity: number;
  totalStock: number;
  lastupdate: string | null;
  lowStock: boolean;
}
