import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Supplierstate} from "../entity/supplierstate";

@Injectable({
  providedIn: 'root'
})
export class Supplierstateservice {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Supplierstate>> {

    const supplierstates = await this.http.get<Array<Supplierstate>>('http://localhost:8080/supplierstates/list').toPromise();
    if(supplierstates == undefined){
      return [];
    }
    return supplierstates;
  }
}
