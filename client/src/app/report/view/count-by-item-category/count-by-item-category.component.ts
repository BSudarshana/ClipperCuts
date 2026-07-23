import { Component, OnInit } from '@angular/core';
import { CountByItemCategory } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-item-category',
  templateUrl: './count-by-item-category.component.html',
  styleUrls: ['./count-by-item-category.component.scss']
})
export class CountByItemCategoryComponent implements OnInit {

  data: Array<CountByItemCategory> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByItemCategory();
    this.loading = false;
  }
}
