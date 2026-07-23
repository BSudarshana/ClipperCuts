import { Component, OnInit } from '@angular/core';

import { CountByServiceCategory } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-service-category',
  templateUrl: './count-by-service-category.component.html',
  styleUrls: ['./count-by-service-category.component.scss']
})
export class CountByServiceCategoryComponent implements OnInit {

  data: Array<CountByServiceCategory> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByServiceCategory();
    this.loading = false;
  }
}
