import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {InventoryLocation, InventoryRecord} from '../entity/inventory';

export interface InventorySearch {
  itemnumber?: string;
  name?: string;
  locationId?: number;
  lowStock?: boolean;
}

@Injectable({providedIn: 'root'})
export class InventoryService {
  private readonly url = 'http://localhost:8080/inventory';

  constructor(private http: HttpClient) {}

  getAll(search: InventorySearch = {}): Promise<InventoryRecord[]> {
    let params = new HttpParams();
    if (search.itemnumber?.trim()) params = params.set('itemnumber', search.itemnumber.trim());
    if (search.name?.trim()) params = params.set('name', search.name.trim());
    if (search.locationId != null) params = params.set('locationId', String(search.locationId));
    if (search.lowStock != null) params = params.set('lowStock', String(search.lowStock));

    return this.http.get<InventoryRecord[]>(this.url, {params})
      .toPromise()
      .then(value => value ?? []);
  }

  getLocations(): Promise<InventoryLocation[]> {
    return this.http.get<InventoryLocation[]>(`${this.url}/locations`)
      .toPromise()
      .then(value => value ?? []);
  }
}
