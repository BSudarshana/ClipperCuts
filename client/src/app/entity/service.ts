import {Servicestatus} from "./servicestatus";
import {Servicecategory} from "./servicecategory";

export class Service{
  public  id !: number;
  public code !: string;
  public name !: string;
  public duration !: number;
  public price !: number;
  public servicestatus !: Servicestatus;
  public servicecategory !: Servicecategory

  constructor(id: number,
              code: string,
              name: string,
              duration: number,
              price: number,
              servicestatus: Servicestatus,
              servicecategory: Servicecategory)
  {
    this.id = id;
    this.code = code;
    this.name = name;
    this.duration = duration;
    this.price = price;
    this.servicestatus = servicestatus;
    this.servicecategory = servicecategory;
  }
}
