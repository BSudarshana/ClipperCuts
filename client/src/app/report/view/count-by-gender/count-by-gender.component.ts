import { Component, OnInit } from '@angular/core';
import { CountByGender } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-gender',
  templateUrl: './count-by-gender.component.html',
  styleUrls: ['./count-by-gender.component.scss']
})
export class CountByGenderComponent implements OnInit {

  data: Array<CountByGender> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByGender();
    this.loading = false;
  }
}
