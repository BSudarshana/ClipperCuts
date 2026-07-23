import {Appointmentstatus} from "./appointmentstatus";
import {Customer} from './customer';
import {Service} from './service';
import {Employee} from './employee';


// export class Appointment{
//   public  id !: number;
//   public  appointment_date !: string;
//   public appointment_time !: string;
//   public description !: string;
//   public appointmentstatus !: Appointmentstatus;
//
//
//   constructor(id: number,
//               appointment_date: string,
//               appointment_time: string,
//               description: string,
//               appointmentstatus: Appointmentstatus)
//   {
//     this.id = id;
//     this.appointment_date = appointment_date;
//     this.appointment_time = appointment_time;
//     this.description = description;
//     this.appointmentstatus = appointmentstatus;
//   }
// }

export interface AppointmentServiceLine {
  id?: number;
  service: Service;
  employee: Employee | null;
  startTime: string;
  endTime: string;
  agreedPrice: number;
}

export interface Appointment {
  id?: number;
  appointmentDate: string;
  appointmentTime: string;
  description?: string;

  appointmentstatus: Appointmentstatus;
  customer: Customer;

  appointmentservices?: AppointmentServiceLine[];

}

export interface AppointmentLineRequest {
  serviceId: number;
  employeeId: number | null;
}

export interface AppointmentCreateRequest {
  appointmentDate: string;
  appointmentTime: string;
  description: string;
  customerId: number;
  services: AppointmentLineRequest[];
}


