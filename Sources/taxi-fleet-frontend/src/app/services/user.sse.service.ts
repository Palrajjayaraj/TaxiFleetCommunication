import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Booking } from '../model/booking.model';
import { User } from '../model/user.model';

/**
 * The service which listens to booking confirmation from server, as a user.
 */
@Injectable({
    providedIn: 'root'
})
export class UserBookingEventsService {

    private bookingConfirmationNotificationsUrl = environment.BASE_URL + 'notifications/user/bookings/subscribe/';

    private static readonly BOOKING_CONFIRM = 'booking-confirmation';

    constructor(private zone: NgZone) { }


    listenToBookingConfirmations(user: User): Observable<Booking> {
        return this.createSseObservable<Booking>(user.uuid, this.bookingConfirmationNotificationsUrl, UserBookingEventsService.BOOKING_CONFIRM);
    }

    private createSseObservable<T>(userID: string, baseURL: string, eventType: string): Observable<T> {
        const url = `${baseURL}${userID}`;
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
