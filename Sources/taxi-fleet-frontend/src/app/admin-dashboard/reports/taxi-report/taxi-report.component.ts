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
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

import { Taxi, TaxiStatus } from '../../../model/taxi.model';
import { CommonService } from '../../../services/common.service';

@Component({
  selector: 'app-taxi-report',
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
  templateUrl: './taxi-report.component.html',
  styleUrls: ['./taxi-report.component.css']
})
export class TaxiReportComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['numberPlate', 'status', 'location'];
  
  filteredDataSource = new MatTableDataSource<Taxi>();

  statusOptions: string[] = Object.values(TaxiStatus);

  selectedStatus: string = ''; 

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taxiService: CommonService) {}

  ngOnInit(): void {
    this.filteredDataSource.filterPredicate = (data: Taxi, filter: string): boolean => {
      return data.currentStatus.toLowerCase().includes(filter);
    };
    this.loadTaxis();
  }

  ngAfterViewInit(): void {
    this.filteredDataSource.paginator = this.paginator;
    this.filteredDataSource.sort = this.sort;
  }

  loadTaxis() {
    this.taxiService.getTaxis().subscribe(data => {
      this.filteredDataSource.data = data;
      this.applyStatusFilter();
    });
  }

  applyStatusFilter() {
    this.filteredDataSource.filter = this.selectedStatus.trim().toLowerCase();
  }
}
