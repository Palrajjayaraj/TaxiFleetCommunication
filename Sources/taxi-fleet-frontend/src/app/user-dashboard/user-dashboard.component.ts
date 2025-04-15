import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { map, Observable, startWith } from 'rxjs';
import { Location } from '../model/location.model';
import { CommonService } from '../services/common.service';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { StateService } from '../services/state.service';
import { User } from '../model/user.model';

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
    HttpClientModule,
    FlexLayoutModule
  ],
  providers: [CommonService],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {
  constructor(private router: Router, private locationService: CommonService, private stateService: StateService) { }

  loggedInUser!: User;

  showBookingForm: boolean = false;

  sourceControl = new FormControl('');

  destinationControl = new FormControl('');

  allLocations: Location[] = [];

  filteredSources!: Observable<Location[]>;

  filteredDestinations!: Observable<Location[]>;

  ngOnInit(): void {
    this.loggedInUser = this.stateService.currentUser;
    this.locationService.getLocations().subscribe(locations =>
      this.allLocations = locations
    );

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
