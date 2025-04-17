import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { BookingRequest, UserBookingRequest as InitialBookingRequest } from '../model/booking.request.model';
import { TaxiResponsePayload } from '../model/taxi.response.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
    private bookingRequestUrl = environment.BASE_URL + 'bookings/request';

    private static readonly TAXI_RESPONSE_URL = environment.BASE_URL + 'bookings/taxi/response';

    constructor(private http: HttpClient) { }

    /** connects with the bakend service and requests for booking. */
    requestBooking(request: InitialBookingRequest): Observable<BookingRequest> {
        return this.http.post<BookingRequest>(this.bookingRequestUrl, request);
    }

    response(taxiResponse: TaxiResponsePayload): void {
        this.http.post(BookingService.TAXI_RESPONSE_URL, taxiResponse).subscribe(data => {
            console.log("Taxi response sent successfully");
        });
    }

}
