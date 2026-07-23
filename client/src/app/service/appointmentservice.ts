// import {Injectable} from "@angular/core";
// import {HttpClient} from "@angular/common/http";
// import {Appointment} from "../entity/appointment";
//
// @Injectable
// ({
//   providedIn : 'root'
// })
// export class Appointmentservice{
//   private baseUrl = 'http://localhost:8080/suppliers';
//
//   constructor(private http : HttpClient) {  }
//
//   async getAll() : Promise<Array<Appointment>>{
//     const appointments = await this.http.get<Array<Appointment>>(`${this.baseUrl}`).toPromise();
//     if(appointments == null){
//       return [];
//     }
//
//     return appointments;
//   }
// }

import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {Appointment, AppointmentCreateRequest} from '../entity/appointment';
import {ApiResponse}  from './apiresponse';
import {Employee} from '../entity/employee';

@Injectable({providedIn: 'root'})
export class AppointmentService {
  private readonly baseUrl = 'http://localhost:8080/appointments';
  private readonly lineUrl = 'http://localhost:8080/appointmentservices';

  constructor(private http: HttpClient) {}

  async getAll(query = ''): Promise<Appointment[]> {
    return (await firstValueFrom(
      this.http.get<Appointment[]>(`${this.baseUrl}${query}`)
    )) ?? [];
  }

  async getById(id: number): Promise<Appointment | null> {
    const appointment = await this.http
      .get<Appointment>(
        `${this.baseUrl}/${id}`
      )
      .toPromise();

    return appointment ?? null;
  }

  async add(request: AppointmentCreateRequest): Promise<ApiResponse> {
    return firstValueFrom(this.http.post<ApiResponse>(this.baseUrl, request));
  }

  async update(
    id: number,
    request: AppointmentCreateRequest
  ): Promise<ApiResponse> {

    const response = await this.http
      .put<ApiResponse>(
        `${this.baseUrl}/${id}`,
        request
      )
      .toPromise();

    if (response === undefined) {
      return {
        errors: 'No response received from the server.'
      };
    }

    return response;
  }

  async getAvailableEmployees(
    serviceId: number,
    date: string,
    startTime: string
  ): Promise<Employee[]> {
    const params = new HttpParams()
      .set('serviceId', serviceId)
      .set('date', date)
      .set('startTime', this.withSeconds(startTime));

    return (await firstValueFrom(
      this.http.get<Employee[]>(`${this.lineUrl}/available-employees`, {params})
    )) ?? [];
  }

  async assignToMe(lineId: number): Promise<ApiResponse> {
    return firstValueFrom(
      this.http.put<ApiResponse>(`${this.lineUrl}/${lineId}/assign-to-me`, {})
    );
  }

  async start(lineId: number): Promise<ApiResponse> {
    return firstValueFrom(
      this.http.put<ApiResponse>(`${this.lineUrl}/${lineId}/start`, {})
    );
  }

  private withSeconds(time: string): string {
    return time.length === 5 ? `${time}:00` : time;
  }
}

