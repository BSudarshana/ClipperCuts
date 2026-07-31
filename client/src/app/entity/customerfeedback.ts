import {Customer} from './customer';
import {Appointment} from './appointment';
import {Rating} from "./rating";

export interface Customerfeedback{
  id?:number;
  date:string;
  comment:string;
  rating:Rating;
  customer:Customer;
  appointment:Appointment;
}
