import {Itemstatus} from "./itemstatus";
import {Unittype} from "./unittype";
import {Itembrand} from "./itembrand";
import {Subcategory} from "./subcategory";

export interface Item{
  id:number;
  name:string;
  itemnumber:string;
  dointroduced : string;
  quantity : number;
  sprice : number;
  pprice : number;
  rop: number;
  itemstatus : Itemstatus;
  unittype : Unittype;
  itembrand : Itembrand;
  subcategory : Subcategory;
}




