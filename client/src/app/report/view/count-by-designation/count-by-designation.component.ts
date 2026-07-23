import { Component, OnInit } from '@angular/core';
import { CountByDesignation } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-designation',
  templateUrl: './count-by-designation.component.html',
  styleUrls: ['./count-by-designation.component.scss']
})
export class CountByDesignationComponent implements OnInit {

  data: Array<CountByDesignation> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByDesignation();
    this.loading = false;
  }
}
