import {Cusstatus} from "../entity/cusstatus";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class Cusstatusservice {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Cusstatus>> {

    const customerstatuses = await this.http.get<Array<Cusstatus>>('http://localhost:8080/customersstatuses/list').toPromise();
    if(customerstatuses == undefined){
      return [];
    }
    return customerstatuses;
  }

}


