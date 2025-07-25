import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Item} from "../entity/item";

@Injectable({
  providedIn: 'root'
})

export class Itemservice {

  constructor(private http: HttpClient) {  }

  async getAll(query:string): Promise<Array<Item>> {
    const items = await this.http.get<Array<Item>>('http://localhost:8080/items'+query).toPromise();
    if(items == undefined){
      return [];
    }
    return items;
  }

  async getAllLsit(): Promise<Array<Item>> {
    const items = await this.http.get<Array<Item>>('http://localhost:8080/items/list').toPromise();
    if(items == undefined){
      return [];
    }
    return items;
  }

  async add(item: Item): Promise<[]|undefined>{
    return this.http.post<[]>('http://localhost:8080/items', item).toPromise();
  }

  async update(item: Item): Promise<[]|undefined>{
    return this.http.put<[]>('http://localhost:8080/items', item).toPromise();
  }

  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete('http://localhost:8080/items/' + id).toPromise();
  }

}


