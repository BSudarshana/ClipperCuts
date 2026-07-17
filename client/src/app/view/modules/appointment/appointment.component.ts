import { Component } from '@angular/core';
import {Appointment} from "../../../entity/appointment";
import {Appointmentservice} from "../../../service/appointmentservice";
import {Customer} from "../../../entity/customer";
import {CustomerService} from "../../../service/customerservice";
import {Appointmentstatus} from "../../../entity/appointmentstatus";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthorizationManager} from "../../../service/authorizationmanager";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent {

  appointmentstatuses : Array<Appointmentstatus> = [];

  constructor(public authService : AuthorizationManager,
              private formbuilder : FormBuilder) {
  }


  public form !: FormGroup;

  ngOninit(){
    this.initialize();
  }

  initialize(){
    const authoritiesArray = this.authService.getAuthorities();
  }


}
