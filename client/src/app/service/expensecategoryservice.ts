import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Expensecategory} from '../entity/expensecategory';

@Injectable({providedIn: 'root'})
export class ExpensecategoryService {
  private baseUrl = 'http://localhost:8080/expensecategories';
  constructor(private http: HttpClient) {}
  async getAllList(): Promise<Array<Expensecategory>> {
    return (await this.http.get<Array<Expensecategory>>(`${this.baseUrl}/list`).toPromise()) || [];
  }
}
