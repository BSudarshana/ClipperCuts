export interface Lookup { id: number; name: string; }
export interface Category extends Lookup {}
export interface Subcategory extends Lookup { category: Category; }

export interface Product {
  id?: number;
  itemnumber: string;
  name: string;
  dointroduced: string | null;
  sprice: number;
  pprice: number;
  rop: number;
  totalStock?: number;
  itemstatus: Lookup;
  unittype: Lookup;
  itembrand: Lookup;
  subcategory: Subcategory;
}

export interface ProductRequest {
  itemnumber: string;
  name: string;
  dointroduced: string | null;
  sprice: number;
  pprice: number;
  rop: number;
  itemstatusId: number;
  unittypeId: number;
  itembrandId: number;
  subcategoryId: number;
}

export interface ApiResponse { id?: string; url?: string; errors?: string; }
