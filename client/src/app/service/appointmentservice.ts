import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Appointment} from "../entity/appointment";

@Injectable
({
  providedIn : 'root'
})
export class Appointmentservice{
  private baseUrl = 'http://localhost:8080/suppliers';

  constructor(private http : HttpClient) {  }

  async getAll() : Promise<Array<Appointment>>{
    const appointments = await this.http.get<Array<Appointment>>(`${this.baseUrl}`).toPromise();
    if(appointments == null){
      return [];
    }

    return appointments;
  }
}
