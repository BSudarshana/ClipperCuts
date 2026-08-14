import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Expense} from '../entity/expense';

@Injectable({providedIn: 'root'})
export class ExpenseService {
  private baseUrl = 'http://localhost:8080/expenses';
  constructor(private http: HttpClient) {}

  async getAll(query: string): Promise<Array<Expense>> {
    return (await this.http.get<Array<Expense>>(this.baseUrl + query).toPromise()) || [];
  }
  async add(expense: Expense): Promise<any> {
    return this.http.post(this.baseUrl, expense).toPromise();
  }
  async update(expense: Expense): Promise<any> {
    return this.http.put(this.baseUrl, expense).toPromise();
  }
  async delete(id: number): Promise<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).toPromise();
  }
}
