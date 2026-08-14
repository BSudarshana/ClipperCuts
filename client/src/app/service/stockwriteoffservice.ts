import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  StockWriteOff,
  StockWriteOffAvailableItem,
  StockWriteOffCreateResponse,
  StockWriteOffLookup,
  StockWriteOffRequest
} from '../entity/stockwriteoff';

@Injectable({ providedIn: 'root' })
export class StockWriteOffService {
  private readonly baseUrl = 'http://localhost:8080/stockwriteoffs';

  constructor(private http: HttpClient) {}

  getAll(): Observable<StockWriteOff[]> {
    return this.http.get<StockWriteOff[]>(this.baseUrl);
  }

  getById(id: number): Observable<StockWriteOff> {
    return this.http.get<StockWriteOff>(`${this.baseUrl}/${id}`);
  }

  getLocations(): Observable<StockWriteOffLookup[]> {
    return this.http.get<StockWriteOffLookup[]>(`${this.baseUrl}/locations`);
  }

  getAvailableItems(locationId: number): Observable<StockWriteOffAvailableItem[]> {
    const params = new HttpParams().set('locationId', String(locationId));
    return this.http.get<StockWriteOffAvailableItem[]>(`${this.baseUrl}/available-items`, {
      params
    });
  }

  create(request: StockWriteOffRequest): Observable<StockWriteOffCreateResponse> {
    return this.http.post<StockWriteOffCreateResponse>(this.baseUrl, request);
  }
}
