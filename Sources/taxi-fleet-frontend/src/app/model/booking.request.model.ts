import { Location } from './location.model';

export enum BookingRequestStatus {
    PENDING = 'PENDING',
    REJECTED = 'REJECTED',
    ASSIGNED_TAXI = 'ASSIGNED_TAXI'
}

/**represents initial request sent from user. */
export class UserBookingRequest {
    constructor(
        public userId: string,
        public requestTime: Date,
        public pickupLocation: Location,
        public dropoffLocation: Location
    ) { }
}

/**Booking requets POJO model, represents domain model.. */
export class BookingRequest extends UserBookingRequest {
    constructor(
        public uuid: string,
        public override userId: string,
        public override requestTime: Date,
        public override pickupLocation: Location,
        public override dropoffLocation: Location,
        public status: BookingRequestStatus
    ) {
        super(userId, requestTime, pickupLocation, dropoffLocation);
    }

}
