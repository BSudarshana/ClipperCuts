import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Appointmentstatus} from "../entity/appointmentstatus";

@Injectable({
  providedIn : 'root'
})
export class Appointmentstatusservice{

  constructor(private http : HttpClient) {  }

  async getAllList() : Promise<Array<Appointmentstatus>>{

    const appointmentstatuses = await this.http.get<Array<Appointmentstatus>>('http://localhost:8080/appointmentstatus/list').toPromise();

    if(appointmentstatuses == null){
      return [];
    }
    return appointmentstatuses;
  }

}
