import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Supplierstype} from "../entity/supplierstype";

@Injectable({
  providedIn: 'root'
})
export class Supplierstypeservice{

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Supplierstype>> {

    const supplierstypes = await this.http.get<Array<Supplierstype>>('http://localhost:8080/supplierstates/list').toPromise();
    if(supplierstypes == undefined){
      return [];
    }
    return supplierstypes;
  }
}
