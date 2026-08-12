import { Employee } from "./employee";
import { Grnstatus } from "./grntatus";
import {Location} from "./location";
import {Grnitem} from "./grnitem";


export class Purchaseorder {
  id?: number;
  grnNumber?: string;
  date!: string;
  totalAmount!: number;
  description!: string;
  grnstatus!: Grnstatus;
  employee!: Employee;
  grnitems: Grnitem[] = [];
  location!: Location;
}
