import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { Observable } from 'rxjs/internal/Observable';
import { Location } from '../model/location.model';
import { Taxi, TaxiStatus } from '../model/taxi.model';
import { CommonService } from '../services/common.service';
import { StateService } from '../services/state.service';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';

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
    MatInputModule
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

  constructor(
    private commonService: CommonService,
    private stateService: StateService
    // private sseService: SseService
  ) {
    this.locationList$ = commonService.getLocations();
    // this.incomingRequest$ = this.sseService.getBookingEvents();
  }

  ngOnInit(): void {
    this.loggedInTaxi = this.stateService.currentEntity as Taxi;
    this.locationControl.setValue(this.loggedInTaxi.currentLocation);
    this.statusControl.setValue(this.loggedInTaxi.status);
    this.addListeners();
    this.locationList$.subscribe(list => {
      const locations = list;
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
    //   const taxi = this.state.currentTaxi as Taxi;
    //   this.state.updateCurrentTaxi({
    //     ...taxi,
    //     currentLocation: location
    //   });
  }

  onStatusChange(status: TaxiStatus): void {
    //   const taxi = this.state.currentTaxi as Taxi;
    //   this.state.updateCurrentTaxi({
    //     ...taxi,
    //     status
    //   });
  }
}