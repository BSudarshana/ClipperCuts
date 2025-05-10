import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Custype} from "../entity/custype";

@Injectable({
  providedIn: 'root'
})

export class Custypeservice {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Custype>> {

    const customerstypes = await this.http.get<Array<Custype>>('http://localhost:8080/customerstypes/list').toPromise();
    if(customerstypes == undefined){
      return [];
    }
    return customerstypes;
  }

}


