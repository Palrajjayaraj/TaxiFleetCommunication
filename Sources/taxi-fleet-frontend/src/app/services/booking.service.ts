import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BookingRequest, UserBookingRequest as InitialBookingRequest } from '../model/booking.request.model';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class BookingService {
    private bookingRequestUrl = environment.BASE_URL + 'bookings/request';


    constructor(private http: HttpClient) { }

    /** connects with the bakend service and requests for booking. */
    requestBooking(request: InitialBookingRequest): Observable<BookingRequest> {
        return this.http.post<BookingRequest>(this.bookingRequestUrl, request);
    }

}
