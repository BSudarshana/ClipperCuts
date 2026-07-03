import {Appointmentstatus} from "./appointmentstatus";

export class Appointment{
  public  id !: number;
  public  appointment_date !: string;
  public appointment_time !: string;
  public description !: string;
  public appointmentstatus !: Appointmentstatus;


  constructor(id: number,
              appointment_date: string,
              appointment_time: string,
              description: string,
              appointmentstatus: Appointmentstatus)
  {
    this.id = id;
    this.appointment_date = appointment_date;
    this.appointment_time = appointment_time;
    this.description = description;
    this.appointmentstatus = appointmentstatus;
  }
}

