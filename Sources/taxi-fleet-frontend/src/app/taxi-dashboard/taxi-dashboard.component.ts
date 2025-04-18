import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { Observable } from 'rxjs/internal/Observable';
import { BookingRequestDialogComponent } from '../booking-request-dialog/booking-request-dialog.component';
import { Location } from '../model/location.model';
import { Taxi, TaxiResponse, TaxiStatus } from '../model/taxi.model';
import { TaxiResponsePayload } from '../model/taxi.response.model';
import { BookingService } from '../services/booking.service';
import { CommonService } from '../services/common.service';
import { StateService } from '../services/state.service';
import { TaxiService } from '../services/taxi.service';
import { TaxiBookingEventsService } from '../services/taxi.sse.service';
import { Booking } from '../model/booking.model';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Component({
  selector: 'app-taxi-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatListModule,
    MatAutocompleteModule,
    MatInputModule,
    MatDialogModule
  ],
  templateUrl: './taxi-dashboard.component.html',
  styleUrls: ['./taxi-dashboard.component.css']
})
export class TaxiDashboardComponent implements OnInit {

  loggedInTaxi!: Taxi;

  locationList$: Observable<Location[]>;

  locationControl = new FormControl<Location | null>(null);

  statusControl = new FormControl<TaxiStatus | null>(null);

  statusOptions = Object.values(TaxiStatus);

  currentBooking$: BehaviorSubject<Booking | null> = new BehaviorSubject<Booking | null>(null);

  constructor(
    private commonService: CommonService,
    private stateService: StateService,
    private taxiService: TaxiService,
    private bookingService: BookingService,
    private notificationService: TaxiBookingEventsService,
    private dialog: MatDialog
  ) {
    this.locationList$ = commonService.getLocations();
  }

  ngOnInit(): void {
    this.loggedInTaxi = this.stateService.currentEntity as Taxi;
    this.locationControl.setValue(this.loggedInTaxi.currentLocation);
    this.statusControl.setValue(this.loggedInTaxi.currentStatus);
    this.addListeners();
    this.addSseListeners();
    this.locationList$.subscribe(list => {
      const locations = list;
    });
  }
  addSseListeners() {
    this.addBookingRequestListener();
    this.addBookingListener();
  }

  addBookingListener() {
    this.notificationService.listenToBookingConfirmations(this.loggedInTaxi).subscribe(booking => {
      if (booking) {
        this.loggedInTaxi.currentStatus = TaxiStatus.BOOKED;
        this.onStatusChange(this.loggedInTaxi.currentStatus);
        this.currentBooking$.next(booking);
      }
    });
  }

  addBookingRequestListener() {
    this.notificationService.listenToBookingRequests(this.loggedInTaxi).subscribe(request => {
      if (TaxiStatus.AVAILABLE === this.loggedInTaxi.currentStatus) {
        const dialogRef = this.dialog.open(BookingRequestDialogComponent, {
          width: '500px',
          height: '300px',
          data: request
        });

        dialogRef.afterClosed().subscribe(result => {
          if (result === TaxiResponse.ACCEPTED) {
            this.bookingService.response(new TaxiResponsePayload(this.loggedInTaxi.id, request.uuid, TaxiResponse.ACCEPTED));
          } else {
            this.bookingService.response(new TaxiResponsePayload(this.loggedInTaxi.id, request.uuid, TaxiResponse.REJECTED));
          }
        });
      }
    });
  }

  private addListeners(): void {
    this.locationControl.valueChanges.subscribe(loc => {
      if (loc) this.onLocationChange(loc);
    });
    this.statusControl.valueChanges.subscribe(status => {
      if (status) this.onStatusChange(status);
    });
  }

  displayLocationName(location: Location): string {
    return location?.readableName ?? '';
  }

  onLocationChange(location: Location): void {
    this.loggedInTaxi.currentLocation = location;
    this.taxiService.updateTaxiState(this.loggedInTaxi);
  }

  onStatusChange(status: TaxiStatus): void {
    if (this.loggedInTaxi.currentStatus === TaxiStatus.BOOKED && status !== TaxiStatus.BOOKED) {
      this.currentBooking$.next(null);
    }
    this.loggedInTaxi.currentStatus = status;
    this.taxiService.updateTaxiState(this.loggedInTaxi);
  }
}
