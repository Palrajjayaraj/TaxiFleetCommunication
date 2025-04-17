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

    private bookingConfirmationNotificationsUrl = environment.BASE_URL + 'notifications/taxi/bookings/subscribe/';

    private static readonly BOOKING_CONFIRM = 'booking-confirmation';

    private static readonly BOOKING_REQUEST = 'booking-request';

    constructor(private zone: NgZone) { }

    listenToBookingRequests(taxi: Taxi): Observable<BookingRequest> {
        return this.createSseObservable<BookingRequest>(taxi.id, this.bookingRequestNotificationsUrl, BookingEventsService.BOOKING_REQUEST);
    }

    listenToBookingConfirmations(taxi: Taxi): Observable<Booking> {
        return this.createSseObservable<Booking>(taxi.id, this.bookingConfirmationNotificationsUrl, BookingEventsService.BOOKING_CONFIRM);
    }

    private createSseObservable<T>(taxiId: string, baseURL: string, eventType: string): Observable<T> {
        const url = `${baseURL}${taxiId}`;
        return new Observable(observer => {
            const eventSource = new EventSource(url);

            eventSource.addEventListener(eventType, (event: MessageEvent) => {
                this.zone.run(() => {
                    const data = JSON.parse(event.data);
                    observer.next(data);
                });
            });

            eventSource.onerror = (error) => {
                console.error('SSE error', error);
                eventSource.close();
                observer.error(error);
            };

            return () => eventSource.close();
        });
    }
}
