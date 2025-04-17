import { Component, Inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BookingRequest } from '../model/booking.request.model';
import { TaxiResponse } from '../model/taxi.model';
import { MatButtonModule } from '@angular/material/button';

@Component({
  imports: [MatIconModule,MatButtonModule],
  standalone: true,
  selector: 'app-booking-request-dialog',
  templateUrl: './booking-request-dialog.component.html',
  styleUrl: './booking-request-dialog.component.css'
})
export class BookingRequestDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<BookingRequestDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BookingRequest
  ) { }

  accept() {
    this.dialogRef.close(TaxiResponse.ACCEPTED);
  }

  reject() {
    this.dialogRef.close(TaxiResponse.REJECTED);
  }
}