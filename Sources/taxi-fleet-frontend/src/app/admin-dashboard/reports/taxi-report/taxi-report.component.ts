import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

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

  constructor(private taxiService: CommonService) { }

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
