import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CountByAppointmentStatus, CountByCustomerType, CountByDesignation, CountByEmpStatus, CountByGender, CountByItemCategory, CountByServiceCategory, RevenueByMonth, RevenueByPaymentMethod, TotalByPoSupplier } from './entity/report-models';


const BASE_URL = 'http://localhost:8080/reports';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  async countByDesignation(): Promise<Array<CountByDesignation>> {
    const data = await this.http.get<Array<CountByDesignation>>(`${BASE_URL}/countbydesignation`).toPromise();
    return data ?? [];
  }

  async countByEmpStatus(): Promise<Array<CountByEmpStatus>> {
    const data = await this.http.get<Array<CountByEmpStatus>>(`${BASE_URL}/countbyempstatus`).toPromise();
    return data ?? [];
  }

  async countByCustomerType(): Promise<Array<CountByCustomerType>> {
    const data = await this.http.get<Array<CountByCustomerType>>(`${BASE_URL}/countbycustomertype`).toPromise();
    return data ?? [];
  }

  async countByGender(): Promise<Array<CountByGender>> {
    const data = await this.http.get<Array<CountByGender>>(`${BASE_URL}/countbygender`).toPromise();
    return data ?? [];
  }

  async countByAppointmentStatus(): Promise<Array<CountByAppointmentStatus>> {
    const data = await this.http.get<Array<CountByAppointmentStatus>>(`${BASE_URL}/countbyappointmentstatus`).toPromise();
    return data ?? [];
  }

  async revenueByPaymentMethod(): Promise<Array<RevenueByPaymentMethod>> {
    const data = await this.http.get<Array<RevenueByPaymentMethod>>(`${BASE_URL}/revenuebypaymentmethod`).toPromise();
    return data ?? [];
  }

  async countByItemCategory(): Promise<Array<CountByItemCategory>> {
    const data = await this.http.get<Array<CountByItemCategory>>(`${BASE_URL}/countbyitemcategory`).toPromise();
    return data ?? [];
  }

  async countByServiceCategory(): Promise<Array<CountByServiceCategory>> {
    const data = await this.http.get<Array<CountByServiceCategory>>(`${BASE_URL}/countbyservicecategory`).toPromise();
    return data ?? [];
  }

  async totalByPoSupplier(): Promise<Array<TotalByPoSupplier>> {
    const data = await this.http.get<Array<TotalByPoSupplier>>(`${BASE_URL}/totalbyposupplier`).toPromise();
    return data ?? [];
  }

  async revenueByMonth(): Promise<Array<RevenueByMonth>> {
    const data = await this.http.get<Array<RevenueByMonth>>(`${BASE_URL}/revenuebymonth`).toPromise();
    return data ?? [];
  }
}
