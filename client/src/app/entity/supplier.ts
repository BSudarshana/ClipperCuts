import {Supplierstate} from "./supplierstate";
import {Supplierstype} from "./supplierstype";

export class Supplier{

  public id !: number;
  public name !: string;
  public registernumber !: string;
  public address !: string;
  public contactnumber !: string;
  public contactperson !: string;
  public email !: string;
  public doregister !: string;
  public description !: string;
  public supplierstate !: Supplierstate;
  public supplierstype !: Supplierstype;

  constructor(id:number,
              name:string,
              registernumber:string,
              address:string,
              contactnumber:string,
              contactperson:string,
              email:string,
              doregister:string,
              description:string,
              supplierstate:Supplierstate,
              supplierstype:Supplierstype ) {

    this.id=id;
    this.name=name;
    this.registernumber=registernumber;
    this.address=address;
    this.contactnumber=contactnumber;
    this.contactperson=contactperson;
    this.email=email;
    this.doregister=doregister;
    this.description=description;
    this.supplierstate=supplierstate;
    this.supplierstype=supplierstype;
  }

}





