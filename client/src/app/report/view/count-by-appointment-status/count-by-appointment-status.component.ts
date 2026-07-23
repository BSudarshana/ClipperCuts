import { Component, OnInit } from '@angular/core';
import { CountByAppointmentStatus } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-count-by-appointment-status',
  templateUrl: './count-by-appointment-status.component.html',
  styleUrls: ['./count-by-appointment-status.component.scss']
})
export class CountByAppointmentStatusComponent implements OnInit {

  data: Array<CountByAppointmentStatus> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.countByAppointmentStatus();
    this.loading = false;
  }
}
