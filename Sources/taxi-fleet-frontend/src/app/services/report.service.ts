import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { BookingRequestsConversion, BookingTrend } from '../model/booking.trend.model';

/**Report service which connects to server and gets report specific data. */
@Injectable({
    providedIn: 'root'
})
export class ReportService {

    private static readonly BOOKING_TREND_URL = environment.BASE_URL + 'report/bookings/trend';

    private static readonly BOOKING_REQUEST_CONVERSION_URL = environment.BASE_URL + 'report/bookingRequests/trend';

    constructor(private http: HttpClient) { }

    getBookingTrends(): Observable<BookingTrend[]> {
        return this.http.get<BookingTrend[]>(ReportService.BOOKING_TREND_URL);
    }

    getBookingRequestsConversion(): Observable<BookingRequestsConversion[]> {
        return this.http.get<BookingRequestsConversion[]>(ReportService.BOOKING_REQUEST_CONVERSION_URL);
    }
}
