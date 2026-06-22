import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Supplier} from "../entity/supplier";

@Injectable({
  providedIn: 'root'
})

export class SupplierService {

  private baseUrl = 'http://localhost:8080/suppliers';

  constructor(private http: HttpClient) {  }

  async add(supplier: Supplier): Promise<[]|undefined>{
    return this.http.post<[]>(`${this.baseUrl}`, supplier).toPromise();
  }


  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete(`${this.baseUrl}` +'/' + id).toPromise();
  }

  async update(supplier: Supplier): Promise<[]|undefined>{
    return this.http.put<[]>(`${this.baseUrl}`, supplier).toPromise();
  }

  async getAll(query:string): Promise<Array<Supplier>> {
    const suppliers = await this.http.get<Array<Supplier>>(`${this.baseUrl}`+query).toPromise();
    if(suppliers == undefined){
      return [];
    }
    return suppliers;
  }

  // async getAllListNameId(): Promise<Array<Supplier>> {
  //   const suppliers = await this.http.get<Array<Supplier>>(`${this.baseUrl}`+"/list").toPromise();
  //   if(suppliers == undefined){
  //     return [];
  //   }
  //   return suppliers;
  // }

}

