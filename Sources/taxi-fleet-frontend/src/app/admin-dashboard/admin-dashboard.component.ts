import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';

import { TaxiReportComponent } from './reports/taxi-report/taxi-report.component';
import { BookingReportComponent } from './reports/booking-report/booking-report.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  imports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    TaxiReportComponent,
    BookingReportComponent
  ]
})
export class AdminDashboardComponent {
  private selectedReportSubject = new BehaviorSubject<string>('Taxi Report');
  selectedReport$ = this.selectedReportSubject.asObservable();

  selectReport(report: string): void {
    this.selectedReportSubject.next(report);
  }
}
