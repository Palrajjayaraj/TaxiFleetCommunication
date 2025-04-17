import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-booking-report',
  standalone: true,
  imports: [CommonModule], // Add other needed modules like MatTableModule if used
  templateUrl: './booking-report.component.html',
  styleUrls: ['./booking-report.component.css']
})
export class BookingReportComponent {}
