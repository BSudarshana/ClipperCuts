import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, ElementRef, NgModule } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { CountByDesignation, CountByEmpStatus, CountByCustomerType, CountByGender, CountByAppointmentStatus, RevenueByPaymentMethod, CountByItemCategory, CountByServiceCategory } from 'src/app/report/entity/report-models';
import { ReportService } from 'src/app/report/report.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

Chart.register(...registerables);

// Global chart typography — keeps every chart consistent with the rest of the UI
Chart.defaults.font.family = "'Inter', -apple-system, BlinkMacSystemFont, sans-serif";
Chart.defaults.color = '#6b6b76';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  @ViewChild('designationChart') designationChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('empStatusChart') empStatusChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('customerTypeChart') customerTypeChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('genderChart') genderChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('appointmentStatusChart') appointmentStatusChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('paymentMethodChart') paymentMethodChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('itemCategoryChart') itemCategoryChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('serviceCategoryChart') serviceCategoryChartRef!: ElementRef<HTMLCanvasElement>;

  reportsLoading = true;

  // Derived KPI totals — computed from the same report calls, not separate fetches
  totalEmployees = 0;
  totalCustomers = 0;
  totalAppointments = 0;
  totalServices = 0;

  // One deliberate brand palette — muted jewel tones, salon/beauty-appropriate,
  // used consistently per-category rather than randomly assigned.
  private readonly palette = [
    '#6B3F69', // plum      — brand primary
    '#C9A227', // gold
    '#3E8A78', // sage teal
    '#B65C6B', // dusty rose
    '#4B5A85', // slate
    '#B0623F', // terracotta
    '#D8C39B', // sand
    '#6B6B76'  // graphite
  ];

  // Per-chart accent so each bar chart reads as its own category, matching the
  // colored dot rendered next to its card title in the template.
  readonly accent = {
    designation: '#6B3F69',
    empStatus: '#4B5A85',
    appointmentStatus: '#C9A227',
    itemCategory: '#B0623F',
    serviceCategory: '#3E8A78'
  };

  constructor(private reportService: ReportService) {}

  async ngOnInit(): Promise<void> {
    await this.loadReports();
  }

  private async loadReports(): Promise<void> {
    const designation: CountByDesignation[] = await this.reportService.countByDesignation();
    const empStatus: CountByEmpStatus[] = await this.reportService.countByEmpStatus();
    const customerType: CountByCustomerType[] = await this.reportService.countByCustomerType();
    const gender: CountByGender[] = await this.reportService.countByGender();
    const appointmentStatus: CountByAppointmentStatus[] = await this.reportService.countByAppointmentStatus();
    const paymentMethod: RevenueByPaymentMethod[] = await this.reportService.revenueByPaymentMethod();
    const itemCategory: CountByItemCategory[] = await this.reportService.countByItemCategory();
    const serviceCategory: CountByServiceCategory[] = await this.reportService.countByServiceCategory();

    // KPI row totals, derived from data already on hand
    this.totalEmployees = designation.reduce((sum, d) => sum + d.count, 0);
    this.totalCustomers = customerType.reduce((sum, d) => sum + d.count, 0);
    this.totalAppointments = appointmentStatus.reduce((sum, d) => sum + d.count, 0);
    this.totalServices = serviceCategory.reduce((sum, d) => sum + d.count, 0);

    this.reportsLoading = false;

    setTimeout(() => {
      this.bar(this.designationChartRef, designation.map(d => d.designation), designation.map(d => d.count), 'Employees', this.accent.designation);
      this.bar(this.empStatusChartRef, empStatus.map(d => d.status), empStatus.map(d => d.count), 'Employees', this.accent.empStatus);
      this.doughnut(this.customerTypeChartRef, customerType.map(d => d.type), customerType.map(d => d.count));
      this.doughnut(this.genderChartRef, gender.map(d => d.gender), gender.map(d => d.count));
      this.bar(this.appointmentStatusChartRef, appointmentStatus.map(d => d.status), appointmentStatus.map(d => d.count), 'Appointments', this.accent.appointmentStatus);
      this.doughnut(this.paymentMethodChartRef, paymentMethod.map(d => d.method), paymentMethod.map(d => d.totalAmount));
      this.bar(this.itemCategoryChartRef, itemCategory.map(d => d.category), itemCategory.map(d => d.count), 'Items', this.accent.itemCategory);
      this.bar(this.serviceCategoryChartRef, serviceCategory.map(d => d.category), serviceCategory.map(d => d.count), 'Services', this.accent.serviceCategory);
    });
  }

  private bar(ref: ElementRef<HTMLCanvasElement>, labels: string[], data: number[], label: string, color: string): void {
    if (!ref?.nativeElement) return;
    new Chart(ref.nativeElement, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label,
          data,
          backgroundColor: color,
          hoverBackgroundColor: this.shade(color, -18),
          borderRadius: 6,
          borderSkipped: false,
          maxBarThickness: 36
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
          tooltip: {
            backgroundColor: '#201A2B',
            padding: 10,
            cornerRadius: 8,
            titleFont: { weight: 'bold', size: 12 },
            bodyFont: { size: 12 },
            displayColors: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            grid: { color: 'rgba(32,26,43,0.06)' },
            ticks: { color: '#8b8b96', font: { size: 11 } }
          },
          x: {
            grid: { display: false },
            ticks: { color: '#8b8b96', font: { size: 11 } }
          }
        }
      }
    });
  }

  private doughnut(ref: ElementRef<HTMLCanvasElement>, labels: string[], data: number[]): void {
    if (!ref?.nativeElement) return;
    new Chart(ref.nativeElement, {
      type: 'doughnut',
      data: {
        labels,
        datasets: [{
          data,
          backgroundColor: this.palette,
          borderWidth: 2,
          borderColor: '#ffffff',
          hoverOffset: 6
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '68%',
        plugins: {
          legend: {
            position: 'bottom',
            labels: { boxWidth: 10, padding: 14, usePointStyle: true, pointStyle: 'circle', color: '#4a4a55', font: { size: 12 } }
          },
          tooltip: {
            backgroundColor: '#201A2B',
            padding: 10,
            cornerRadius: 8
          }
        }
      }
    });
  }

  // Small helper to darken a hex color for hover states, keeping one accent per chart
  private shade(hex: string, percent: number): string {
    const num = parseInt(hex.replace('#', ''), 16);
    const amt = Math.round(2.55 * percent);
    const r = Math.min(255, Math.max(0, (num >> 16) + amt));
    const g = Math.min(255, Math.max(0, ((num >> 8) & 0x00ff) + amt));
    const b = Math.min(255, Math.max(0, (num & 0x0000ff) + amt));
    return `#${(1 << 24 | r << 16 | g << 8 | b).toString(16).slice(1)}`;
  }
}