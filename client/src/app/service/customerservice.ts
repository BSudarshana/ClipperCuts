import {Customer} from "../entity/customer";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Gender} from "../entity/gender";

@Injectable({
  providedIn: 'root'
})

export class CustomerService {

  private baseUrl = 'http://localhost:8080/customers';

  constructor(private http: HttpClient) {  }

  async add(customer: Customer): Promise<[]|undefined>{
    return this.http.post<[]>(`${this.baseUrl}`, customer).toPromise();
  }


  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete(`${this.baseUrl}` + id).toPromise();
  }

  async update(customer: Customer): Promise<[]|undefined>{
    return this.http.put<[]>(`${this.baseUrl}`, customer).toPromise();
  }

  async getAll(query:string): Promise<Array<Customer>> {
    const customers = await this.http.get<Array<Customer>>(`${this.baseUrl}`+query).toPromise();
    if(customers == undefined){
      return [];
    }
    return customers;
  }

  async getAllListNameId(): Promise<Array<Customer>> {
    const customers = await this.http.get<Array<Customer>>('http://localhost:8080/customers').toPromise();
    if(customers == undefined){
      return [];
    }
    return customers;
  }

}


