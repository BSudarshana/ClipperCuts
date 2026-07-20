import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Promotionstatus} from "../entity/promotionstatus";

@Injectable({ providedIn: 'root' })
export class PromotionStatusService {
  private readonly url = 'http://localhost:8080/promotionstatus/list';

  constructor(private http: HttpClient) {}

  async getAllList(): Promise<Promotionstatus[]> {
    return (await this.http.get<Promotionstatus[]>(this.url).toPromise()) ?? [];
  }
}
