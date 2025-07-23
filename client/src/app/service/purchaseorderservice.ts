import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Purchaseorder} from "../entity/purchaseorder";


@Injectable({
  providedIn: 'root'
})

export class PurchaseorderService {


  constructor(private http: HttpClient) {  }

  async getAll(query:string): Promise<Array<Purchaseorder>> {
    const purchaseorders = await this.http.get<Array<Purchaseorder>>('http://localhost:8080/purchaseorders'+query).toPromise();
    if(purchaseorders == undefined){
      return [];
    }
    return purchaseorders;
  }

  async add(purchaseorder: Purchaseorder): Promise<[]|undefined>{
    return this.http.post<[]>('http://localhost:8080/purchaseorders', purchaseorder).toPromise();
  }

  async update(purchaseorder: Purchaseorder): Promise<[]|undefined>{
    return this.http.put<[]>('http://localhost:8080/purchaseorders', purchaseorder).toPromise();
  }

  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete('http://localhost:8080/purchaseorders/' + id).toPromise();
  }
}


