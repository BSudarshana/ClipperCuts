import {Item} from './item';

export class Poitem {
  id?: number;
  quantity!: number;
  unitprice!: number;
  subTotal!: number;
  item!: Item;

  constructor(quantity: number, unitprice: number, subTotal: number, item: Item, id?: number) {
    this.id = id;
    this.quantity = quantity;
    this.unitprice = unitprice;
    this.subTotal = subTotal;
    this.item = item;
  }
}
