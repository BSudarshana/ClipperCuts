import { Component, OnInit } from '@angular/core';
import { CountByCustomerType } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-customer-type',
  templateUrl: './count-by-customer-type.component.html',
  styleUrls: ['./count-by-customer-type.component.scss']
})
export class CountByCustomerTypeComponent implements OnInit {

  data: Array<CountByCustomerType> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByCustomerType();
    this.loading = false;
  }
}
