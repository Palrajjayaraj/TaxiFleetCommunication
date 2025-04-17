// booking-events.service.ts
import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { BookingRequest } from '../model/booking.request.model';
import { environment } from '../../environments/environment';
import { Taxi } from '../model/taxi.model';
import { Booking } from '../model/booking.model';

/**
 * The service which listens to various Booking requests and booking confirmation from server.
 */
@Injectable({
    providedIn: 'root'
})
export class BookingEventsService {

    private bookingRequestNotificationsUrl = environment.BASE_URL + 'notifications/taxi/bookingsRequest/subscribe/';

    private static readonly BOOKING_CONFIRM = 'booking-confirmation';

    private static readonly BOOKING_REQUEST = 'booking-request';

    constructor(private zone: NgZone) { }

    listenToBookingRequests(taxi: Taxi): Observable<BookingRequest> {
        const url = `${this.bookingRequestNotificationsUrl}${taxi.id}`;
        return new Observable(observer => {
            const eventSource = new EventSource(url);
            eventSource.addEventListener(BookingEventsService.BOOKING_REQUEST, (event: MessageEvent) => {
                this.zone.run(() => {
                    const data = JSON.parse(event.data);
                    observer.next(data);
                });
            });

            eventSource.onerror = (error) => {
                console.error("SSE error", error);
                eventSource.close();
                observer.error(error);
            };

            return () => eventSource.close();
        });
    }

    listenerToBookingConfirmation(taxi: Taxi): Observable<Booking> {
        const url = `${this.bookingRequestNotificationsUrl}${taxi.id}`;
        return new Observable(observer => {
            const eventSource = new EventSource(url);
            eventSource.addEventListener(BookingEventsService.BOOKING_CONFIRM, (event: MessageEvent) => {
                this.zone.run(() => {
                    const data = JSON.parse(event.data);
                    observer.next(data);
                });
            });

            eventSource.onerror = (error) => {
                console.error("SSE error", error);
                eventSource.close();
                observer.error(error);
            };

            return () => eventSource.close();
        });
    }
}