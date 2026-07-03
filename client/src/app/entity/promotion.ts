import {Promotionstatus} from "./promotionstatus";

export class Promotion{
  id !: number;
  title !: string;
  description !: string;
  startdate !: string;
  enddate !: string;
  promotionstatus !: Promotionstatus;

  constructor(id: number,
              title: string,
              description: string,
              startdate: string,
              enddate: string,
              promotionstatus: Promotionstatus) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.startdate = startdate;
    this.enddate = enddate;
    this.promotionstatus = promotionstatus;
  }
}
