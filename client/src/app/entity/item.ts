import {Gender} from "./gender";
import {Customerstatus} from "./customerstatus";
import {Customertype} from "./customertype";

export class Item{

  public id !: number;
  public name !: string;


  constructor(id:number,
              name : string
               ) {

    this.id=id;
    this.name = name;

  }

}





