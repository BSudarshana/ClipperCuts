import {Item} from './item';

export class Grnitem {
  id?: number;
  quantity!: number;
  unitcost!: number;
  sub_total!: number;
  item!: Item;

  constructor(quantity: number, unitcost: number, sub_total: number, item: Item, id?: number) {
    this.id = id;
    this.quantity = quantity;
    this.unitcost = unitcost;
    this.sub_total = sub_total;
    this.item = item;
  }
}
