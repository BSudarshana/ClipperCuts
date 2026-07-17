import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Service} from "../entity/service";

@Injectable({
  providedIn : 'root'
})
export class ServiceService{
  private baseUrl = 'http://localhost:8080/services';

  constructor(private http: HttpClient) {  }

  async add(service : Service) : Promise<[]|undefined>{
    return this.http.post<[]>(`${this.baseUrl}`,service).toPromise()
  }

  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete(`${this.baseUrl}` +'/' + id).toPromise();
  }

  async update(service : Service): Promise<[]|undefined>{
    return this.http.put<[]>(`${this.baseUrl}`, service).toPromise();
  }

  async getAll(query:string): Promise<Array<Service>> {
    const services = await this.http.get<Array<Service>>(`${this.baseUrl}`+query).toPromise();
    if(services == undefined){
      return [];
    }
    return services;
  }
}
