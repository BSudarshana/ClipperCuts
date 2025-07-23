import { Item } from "./item";

export class Poitem {

  public id !: number;
  public quantity !: number;
  public sub_total !: number;
  public item !: Item;

  constructor(id: number, quantity: number, sub_total: number, item: Item) {
    this.id = id;
    this.quantity = quantity;
    this.sub_total = sub_total;
    this.item = item;
  }

}
