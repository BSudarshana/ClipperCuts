import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Servicecategory} from "../entity/servicecategory";

@Injectable({
  providedIn : 'root'
})
export class ServiceCategoryService{

  constructor(private http:HttpClient) {  }

  async getAllList(): Promise<Array<Servicecategory>>{
    const serviceCategoryList = await this.http.get<Array<Servicecategory>>('http://localhost:8080/servicecategories/list').toPromise();

    if(serviceCategoryList == undefined){
      return [];
    }
    return serviceCategoryList;
  }
}
