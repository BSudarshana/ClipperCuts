import {Locationtype} from "./locationtype";

export interface Location{
  id ?: number;
  name ?: string;
  description : string;
  locationtype : Locationtype[];
}
