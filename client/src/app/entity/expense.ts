import {Expensecategory} from './expensecategory';
import {Paymentmethod} from './paymentmethod';

export class Expense {
  public id!: number;
  public expenseNumber!: string;
  public paymentDate!: string;
  public amount!: number;
  public description!: string;
  public expensecategory!: Expensecategory;
  public paymentmethod!: Paymentmethod;
  public paidByUsername!: string;
  public createdAt!: string;
}
