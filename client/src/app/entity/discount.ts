import { Discounttype } from './discounttype';
import { Promotion } from './promotion';

export class Discount {
  public id!: number;
  public discountvalue!: number;
  public maximumdiscount!: number | null;
  public discounttype!: Discounttype;
  public promotion!: Promotion;

  constructor(id: number, discountvalue: number, maximumdiscount: number | null,
              discounttype: Discounttype, promotion: Promotion) {
    this.id = id;
    this.discountvalue = discountvalue;
    this.maximumdiscount = maximumdiscount;
    this.discounttype = discounttype;
    this.promotion = promotion;
  }
}
