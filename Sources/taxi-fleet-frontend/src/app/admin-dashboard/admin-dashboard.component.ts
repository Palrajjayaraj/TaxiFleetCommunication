import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BehaviorSubject } from 'rxjs';

import { BookingReportComponent } from './reports/booking-report/booking-report.component';
import { TaxiReportComponent } from './reports/taxi-report/taxi-report.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  imports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule
  ]
})
export class AdminDashboardComponent {

  ReportType = ReportType;

  reportTypes = Object.values(ReportType);

  reportComponentMap = {
    [ReportType.Taxi]: TaxiReportComponent,
    [ReportType.Booking]: BookingReportComponent
  };

  selectedReport$ = new BehaviorSubject<ReportType>(ReportType.Taxi);

  selectReport(report: ReportType): void {
    this.selectedReport$.next(report);
  }
}
enum ReportType {
  Taxi = 'Taxi Report',
  Booking = 'Booking Reports'
}