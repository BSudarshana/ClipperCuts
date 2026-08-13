import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GoodReceivedNote,GrnCreateRequest,GrnCreateResponse,GrnLookup,GrnPurchaseOrder} from '../entity/goodreceivednote';

@Injectable({providedIn:'root'})

export class GoodReceivedNoteService {
  private readonly baseUrl='http://localhost:8080/grns';

  constructor(private http:HttpClient){}

  getAll():Observable<GoodReceivedNote[]>{
    return this.http.get<GoodReceivedNote[]>(this.baseUrl);
  }

  getById(id:number):Observable<GoodReceivedNote>{
    return this.http.get<GoodReceivedNote>(`${this.baseUrl}/${id}`);
  }

  getEligiblePurchaseOrders():Observable<GrnPurchaseOrder[]>{
    return this.http.get<GrnPurchaseOrder[]>(`${this.baseUrl}/eligible-purchaseorders`);
  }
  getLocations():Observable<GrnLookup[]>{
    return this.http.get<GrnLookup[]>(`${this.baseUrl}/locations`);
  }
  create(request:GrnCreateRequest):Observable<GrnCreateResponse>{
    return this.http.post<GrnCreateResponse>(this.baseUrl,request);
  }
}
