import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {
  AvailableStockItem,
  StockTransfer,
  StockTransferCreateResponse,
  StockTransferLookup,
  StockTransferRequest
} from '../entity/stocktransfer';

@Injectable({providedIn: 'root'})
export class StockTransferService {
  private readonly baseUrl = 'http://localhost:8080/stocktransfers';

  constructor(private http: HttpClient) {}

  getAll(): Observable<StockTransfer[]> {
    return this.http.get<StockTransfer[]>(this.baseUrl);
  }

  getById(id: number): Observable<StockTransfer> {
    return this.http.get<StockTransfer>(`${this.baseUrl}/${id}`);
  }

  getLocations(): Observable<StockTransferLookup[]> {
    return this.http.get<StockTransferLookup[]>(`${this.baseUrl}/locations`);
  }

  getEmployees(): Observable<StockTransferLookup[]> {
    return this.http.get<StockTransferLookup[]>(`${this.baseUrl}/employees`);
  }

  getAvailableItems(locationId: number): Observable<AvailableStockItem[]> {
    const params = new HttpParams().set('locationId', String(locationId));
    return this.http.get<AvailableStockItem[]>(`${this.baseUrl}/available-items`, {params});
  }

  create(request: StockTransferRequest): Observable<StockTransferCreateResponse> {
    return this.http.post<StockTransferCreateResponse>(this.baseUrl, request);
  }
}
