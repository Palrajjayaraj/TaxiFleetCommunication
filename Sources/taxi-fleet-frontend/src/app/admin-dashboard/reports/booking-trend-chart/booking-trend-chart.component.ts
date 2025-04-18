import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { BaseChartDirective, NgChartsModule } from 'ng2-charts';
import { BookingTrend } from '../../../model/booking.trend.model';
import { ReportService } from '../../../services/report.service';


@Component({
  selector: 'app-booking-trend-chart',
  standalone: true,
  templateUrl: './booking-trend-chart.component.html',
  imports: [
    CommonModule,
    NgChartsModule
  ]
})
export class BookingTrendChartComponent implements OnInit {
  bookingData: BookingTrend[] = [];

  constructor(private reportService: ReportService) { }

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Bookings',
        fill: true,
        tension: 0.4,
        borderWidth: 2,
        pointRadius: 4,
      }
    ]
  };

  lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: {
      legend: { display: true },
      tooltip: { enabled: true }
    },
    scales: {
      x: { title: { display: true, text: 'Date' } },
      y: {
        title: { display: true, text: 'Number of Bookings' }, ticks: {
          stepSize: 1,
          precision: 0
        },
      }
    }
  };

  ngOnInit(): void {
    this.reportService.getBookingTrends().subscribe(data => {
      this.bookingData = data;
      this.updateChart();
    });
  }

  ngOnChanges(): void {
    this.updateChart();
  }

  private updateChart(): void {
    this.lineChartData.labels = this.bookingData.map(b => b.date);
    this.lineChartData.datasets[0].data = this.bookingData.map(b => b.count);
    if (this.chart) {
      this.chart.update();
    }
  }
}
