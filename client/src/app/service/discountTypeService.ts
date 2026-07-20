import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Discounttype} from "../entity/discounttype";

@Injectable({ providedIn: 'root' })
export class DiscountTypeService {
  private readonly url = 'http://localhost:8080/discounttypes/list';

  constructor(private http: HttpClient) {}

  async getAllList(): Promise<Discounttype[]> {
    return (await this.http.get<Discounttype[]>(this.url).toPromise()) ?? [];
  }
}
