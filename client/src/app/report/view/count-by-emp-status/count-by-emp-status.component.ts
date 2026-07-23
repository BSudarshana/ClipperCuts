import { Component, OnInit } from '@angular/core';
import { CountByEmpStatus } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-emp-status',
  templateUrl: './count-by-emp-status.component.html',
  styleUrls: ['./count-by-emp-status.component.scss']
})
export class CountByEmpStatusComponent implements OnInit {

  data: Array<CountByEmpStatus> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByEmpStatus();
    this.loading = false;
  }
}
