import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { ApiResponse } from "./apiresponse";
import {Discount} from "../entity/discount";

@Injectable({ providedIn: 'root' })
export class DiscountService {
  private readonly baseUrl = 'http://localhost:8080/discounts';

  constructor(private http: HttpClient) {}

  async add(discount: Discount): Promise<ApiResponse | undefined> {
    return this.http.post<ApiResponse>(this.baseUrl, discount).toPromise();
  }

  async update(discount: Discount): Promise<ApiResponse | undefined> {
    return this.http.put<ApiResponse>(this.baseUrl, discount).toPromise();
  }

  async delete(id: number): Promise<ApiResponse | undefined> {
    return this.http.delete<ApiResponse>(`${this.baseUrl}/${id}`).toPromise();
  }

  async getAll(query = ''): Promise<Discount[]> {
    return (await this.http.get<Discount[]>(`${this.baseUrl}${query}`).toPromise()) ?? [];
  }
}
