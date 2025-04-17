import { Component, ViewChild, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';

import { Taxi, TaxiStatus } from '../model/taxi.model';
import { CommonService } from '../services/common.service';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,  
    MatOptionModule
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit, AfterViewInit {
  selectedReport: string = 'Taxi Report';
  displayedColumns: string[] = ['numberPlate', 'status', 'location'];
  dataSource = new MatTableDataSource<Taxi>();

  // Only filter for Status column
  statusOptions: string[] = Object.values(TaxiStatus);
  selectedStatus: string = ''; 

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taxiService: CommonService) {}

  ngOnInit(): void {
    this.dataSource.filterPredicate = (data: Taxi, filter: string): boolean => {
      return data.status.toLowerCase().includes(filter);
    };
    this.loadTaxis();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  selectReport(report: string) {
    this.selectedReport = report;
    if (report === 'Taxi Report') {
      this.loadTaxis();
    }
  }

  loadTaxis() {
    this.taxiService.getTaxis().subscribe(data => {
      this.dataSource.data = data;
      this.applyStatusFilter();
    });
  }

  applyStatusFilter() {
    this.dataSource.filter = this.selectedStatus.trim().toLowerCase();

  }
}
