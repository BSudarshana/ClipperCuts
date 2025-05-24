import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Customertype} from "../entity/customertype";

@Injectable({
  providedIn: 'root'
})

export class Custypeservice {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Customertype>> {

    const customerstypes = await this.http.get<Array<Customertype>>('http://localhost:8080/customerstypes/list').toPromise();
    if(customerstypes == undefined){
      return [];
    }
    return customerstypes;
  }

}


