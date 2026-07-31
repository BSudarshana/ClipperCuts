import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Rating} from "../entity/rating";

@Injectable({
  providedIn: 'root',
})
export class RatingService{
  private baseUrl = 'http://localhost:8080/ratings';

  constructor(private http: HttpClient) {
  }

  async getAll(): Promise<Rating[]> {
    const ratings = await this.http
      .get<Rating[]>(`${this.baseUrl}/list`)
      .toPromise();

    return ratings ?? [];
  }

}
