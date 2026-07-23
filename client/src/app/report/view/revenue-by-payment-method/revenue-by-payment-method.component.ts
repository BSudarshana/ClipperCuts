import { Component, OnInit } from '@angular/core';
import { RevenueByPaymentMethod } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-revenue-by-payment-method',
  templateUrl: './revenue-by-payment-method.component.html',
  styleUrls: ['./revenue-by-payment-method.component.scss']
})
export class RevenueByPaymentMethodComponent implements OnInit {

  data: Array<RevenueByPaymentMethod> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.revenueByPaymentMethod();
    this.loading = false;
  }
}
