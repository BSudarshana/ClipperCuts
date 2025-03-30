import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Unittype} from "../entity/unittype";

@Injectable({
  providedIn: 'root'
})

export class Unittypeservice {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Unittype>> {

    const unittypes = await this.http.get<Array<Unittype>>('http://localhost:8080/unittypes/list').toPromise();
    if(unittypes == undefined){
      return [];
    }
    return unittypes;
  }

}


