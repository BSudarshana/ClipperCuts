import {Gender} from "./gender";
import {Customerstatus} from "./customerstatus";
import {Customertype} from "./customertype";

export class Customer{

  public id !: number;
  public fullname !: string;
  public callingname !: string;
  public code !: string;
  public address !: string;
  public mobile !: string;
  public email !: string;
  public photo !: string;
  public doassignment !: string;
  public gender !: Gender;
  public cusstatus !: Customerstatus;
  public custype !: Customertype;

  constructor(id:number,
              fullname:string,
              code:string,
              callingname:string,
              photo:string,
              address:string,
              mobile:string,
              email:string,
              doassignment:string,
              description:string,
              gender:Gender,
              cusstatus:Customerstatus,
              custype:Customertype ) {

    this.id=id;
    this.fullname=fullname;
    this.code=code;
    this.callingname=callingname;
    this.photo=photo;
    this.address=address;
    this.mobile=mobile;
    this.email=email;
    this.doassignment=doassignment;
    this.gender=gender;
    this.cusstatus=cusstatus;
    this.custype=custype;
  }

}





