import { Employee } from "./employee";
import { Poitem } from "./poitem";
import { Postatus } from "./postatus";
import { Supplier } from "./supplier";

export class Purchaseorder {

  public id !: number;
  public po_number !: string;
  public date !: string;
  public total_amount !: number;
  public description !: string;
  public postatus !: Postatus;
  public employee !: Employee;
  public poitems !: Array<Poitem>;
  public supplier !: Array<Supplier>;

  constructor(id: number, po_number: string, date: string, total_amount: number, description: string, postatus:Postatus, employee:Employee, poitems: Array<Poitem>,supplier: Array<Supplier>) {
    this.id = id;
    this.po_number = po_number;
    this.date = date;
    this.total_amount = total_amount;
    this.description = description;
    this.postatus = postatus;
    this.employee = employee;
    this.poitems = poitems;
    this.supplier = supplier;
  }

}
