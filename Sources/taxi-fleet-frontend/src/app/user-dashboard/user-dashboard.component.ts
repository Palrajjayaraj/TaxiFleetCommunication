import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable, startWith } from 'rxjs';
import { Booking } from '../model/booking.model';
import { BookingRequest, UserBookingRequest } from '../model/booking.request.model';
import { Location } from '../model/location.model';
import { User } from '../model/user.model';
import { BookingService } from '../services/booking.service';
import { CommonService } from '../services/common.service';
import { StateService } from '../services/state.service';
import { UserBookingEventsService } from '../services/user.sse.service';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule, MatButtonModule,
    HttpClientModule
  ],
  providers: [CommonService, BookingService],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {

  constructor(private router: Router, private locationService: CommonService,
    private bookingService: BookingService, private stateService: StateService,
    private notificationService: UserBookingEventsService) { }

  loggedInUser!: User;

  activeBookingRequest$: BehaviorSubject<BookingRequest | null> = new BehaviorSubject<BookingRequest | null>(null);

  pickupLocControl = new FormControl<Location | null>(null);

  dropOffLocControl = new FormControl<Location | null>(null);

  allLocations: Location[] = [];

  filteredSources!: Observable<Location[]>;

  filteredDestinations!: Observable<Location[]>;

  currentBooking$: BehaviorSubject<Booking | null> = new BehaviorSubject<Booking | null>(null);

  ngOnInit(): void {
    this.loggedInUser = this.stateService.currentEntity as User;
    if (!this.loggedInUser) {
      alert('Redirecting to Login Page ...')
      this.router.navigate(['login']);
    }
    this.locationService.getLocations().subscribe(locations => {
      this.allLocations = locations;
      this.filteredSources = this.pickupLocControl.valueChanges.pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value?.readableName ?? ''),
        map(name => this.filterLocations(name))
      );
      this.filteredDestinations = this.dropOffLocControl.valueChanges.pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value?.readableName ?? ''),
        map(name => this.filterLocations(name))
      );
    }
    );
    this.addSseListener();
  }
  addSseListener() {
    this.notificationService.listenToBookingConfirmations(this.loggedInUser).subscribe(booking => {
      if (booking) {
        this.currentBooking$.next(booking);
        this.activeBookingRequest$.next(null);
      }
    });
  }

  private filterLocations(value: string): Location[] {
    const filterValue = value.toLowerCase();
    return this.allLocations.filter(loc =>
      loc.readableName.toLowerCase().includes(filterValue)
    );
  }

  displayLocationName(location: Location): string {
    return location?.readableName ?? '';
  }

  onRequestTaxi() {
    const pickUpLoc = this.pickupLocControl.value;
    const dropOffLoc = this.dropOffLocControl.value;
    if (!pickUpLoc || !dropOffLoc) {
      alert('Please select valid Pickup and Drop off location.');
      return;
    }
    if (pickUpLoc === dropOffLoc) {
      alert('Pickup and Drop off locations cannot be same.');
      return;
    }
    const request = new UserBookingRequest(this.loggedInUser.uuid, new Date(), pickUpLoc, dropOffLoc);
    this.bookingService.requestBooking(request).subscribe(
      bookingRequest => {
        this.activeBookingRequest$.next(bookingRequest);
      });
  }

  validateSelected(control: FormControl<Location | null>) {
    const selected = control.value;
    const match = this.allLocations.find(loc => loc === selected);
    if (!match) {
      control.setValue(null);
    }
  }


}
