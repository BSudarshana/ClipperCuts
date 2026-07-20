import { Promotion } from "../entity/promotion";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {ApiResponse} from "./apiresponse";

@Injectable({ providedIn: 'root' })
export class PromotionService {
  private readonly baseUrl = 'http://localhost:8080/promotions';

  constructor(private http: HttpClient) {}

  async add(promotion: Promotion): Promise<ApiResponse | undefined> {
    return this.http.post<ApiResponse>(this.baseUrl, promotion).toPromise();
  }

  async update(promotion: Promotion): Promise<ApiResponse | undefined> {
    return this.http.put<ApiResponse>(this.baseUrl, promotion).toPromise();
  }

  async delete(id: number): Promise<ApiResponse | undefined> {
    return this.http.delete<ApiResponse>(`${this.baseUrl}/${id}`).toPromise();
  }

  async getAll(query = ''): Promise<Promotion[]> {
    return (await this.http.get<Promotion[]>(`${this.baseUrl}${query}`).toPromise()) ?? [];
  }
}
