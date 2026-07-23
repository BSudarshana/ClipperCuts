import { Component, OnInit } from '@angular/core';
import { TotalByPoSupplier } from '../../entity/report-models';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-total-by-po-supplier',
  templateUrl: './total-by-po-supplier.component.html',
  styleUrls: ['./total-by-po-supplier.component.scss']
})
export class TotalByPoSupplierComponent implements OnInit {

  data: Array<TotalByPoSupplier> = [];
  loading = true;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.load();
  }

  async load(): Promise<void> {
    this.loading = true;
    this.data = await this.reportService.totalByPoSupplier();
    this.loading = false;
  }
}
