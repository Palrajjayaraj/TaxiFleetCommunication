import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { map, Observable, startWith } from 'rxjs';
import { Location } from '../model/location.model';
import { LocationService } from '../services/location.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    HttpClientModule
  ],
  providers: [LocationService],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {
  constructor(private router: Router, private locationService: LocationService) { }

  showBookingForm: boolean = false;

  sourceControl = new FormControl('');

  destinationControl = new FormControl('');

  allLocations: Location[] = [];

  filteredSources!: Observable<Location[]>;
  filteredDestinations!: Observable<Location[]>;

  ngOnInit(): void {
    this.allLocations = this.locationService.getLocations();
    this.filteredSources = this.sourceControl.valueChanges.pipe(
      startWith(''),
      map(value => this.filterLocations(value ?? ''))
    );
    this.filteredDestinations = this.destinationControl.valueChanges.pipe(
      startWith(''),
      map(value => this.filterLocations(value ?? ''))
    );
  }

  private filterLocations(value: string): Location[] {
    const filterValue = value.toLowerCase();
    return this.allLocations.filter(loc =>
      loc.readableName.toLowerCase().includes(filterValue)
    );
  }

  onNewBooking() {
    this.showBookingForm = !this.showBookingForm;
  }

  onRequestTaxi() {
    alert(`Taxi requested from ${this.sourceControl.value} to ${this.destinationControl.value}`);
  }

  onPastBookings() {
    this.router.navigate(['/past-bookings']);
  }
}
