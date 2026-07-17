import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Servicestatus} from "../entity/servicestatus";

@Injectable({
  providedIn: "root"
})
export class ServiceStatusService{

  constructor(private  http:HttpClient) {
  }

  async getAllList(): Promise<Array<Servicestatus>>{
    const serviceStatus = await this.http.get<Array<Servicestatus>>('http://localhost:8080/servicestatuses/list').toPromise();

    if (serviceStatus == undefined){
      return [];
    }
    return serviceStatus;
  }

}
