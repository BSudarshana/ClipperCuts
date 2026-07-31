import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Customerfeedback} from "../entity/customerfeedback";
import {ApiResponse} from "./apiresponse";

@Injectable({
  providedIn: 'root',
})
export class CustomerFeedbackService{
  private baseUrl = 'http://localhost:8080/customerfeedbacks';

  constructor(private http: HttpClient) {  }

  async add(cusfeedback:Customerfeedback): Promise<ApiResponse|undefined>{
    return this.http.post<ApiResponse>(`${this.baseUrl}`, cusfeedback).toPromise();
  }

  async update(
    customerFeedback: Customerfeedback
  ): Promise<ApiResponse | undefined> {
    return this.http
      .put<ApiResponse>(this.baseUrl, customerFeedback)
      .toPromise();
  }

  async delete(id: number): Promise<ApiResponse | undefined> {
    return this.http
      .delete<ApiResponse>(`${this.baseUrl}/${id}`)
      .toPromise();
  }

  async getAll(query = ''): Promise<Customerfeedback[]> {
    const customerFeedback = await this.http
      .get<Customerfeedback[]>(`${this.baseUrl}${query}`)
      .toPromise();

    return customerFeedback ?? [];
  }

}
