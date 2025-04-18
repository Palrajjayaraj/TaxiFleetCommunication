import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { BaseChartDirective, NgChartsModule } from 'ng2-charts';
import { BookingRequestsConversion } from '../../../../model/booking.trend.model';
import { ReportService } from '../../../../services/report.service';

@Component({
  selector: 'app-booking-conversion-chart',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './booking-conversion-chart.component.html'
})
export class BookingConversionChartComponent implements OnInit {

  constructor(private reportService: ReportService) { }

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;


  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: []
  };

  chartOptions: ChartOptions<'bar'> = {
    responsive: true,
    scales: {
      x: {},
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 1,
          precision: 0
        }
      }
    },
    plugins: {
      legend: { position: 'top' },
      tooltip: { enabled: true }
    }
  };

  ngOnInit(): void {
    this.reportService.getBookingRequestsConversion().subscribe((data: BookingRequestsConversion[]) => {
      this.chartData.labels = data.map(d => d.date);
      this.chartData.datasets = [
        {
          label: 'Total Requests',
          data: data.map(d => d.requestCount),
          backgroundColor: '#42a5f5'
        },
        {
          label: 'Confirmed Bookings',
          data: data.map(d => d.bookingCount),
          backgroundColor: '#66bb6a'
        }
      ];
      this.chart?.update();
    });
  }
}
