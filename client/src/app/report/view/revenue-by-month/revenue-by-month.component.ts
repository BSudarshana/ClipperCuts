import { Component, OnInit } from '@angular/core';
import { RevenueByMonth } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-revenue-by-month',
  templateUrl: './revenue-by-month.component.html',
  styleUrls: ['./revenue-by-month.component.scss']
})
export class RevenueByMonthComponent implements OnInit {

  data: Array<RevenueByMonth> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.revenueByMonth();
    this.loading = false;
  }
}
