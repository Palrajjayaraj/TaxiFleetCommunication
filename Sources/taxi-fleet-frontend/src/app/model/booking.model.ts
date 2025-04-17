import { BookingRequest } from "./booking.request.model";

export class Booking {
    public static readonly Status = {
        CONFIRMED: 'CONFIRMED',
        IN_PROGRESS: 'IN_PROGRESS',
        COMPLETED: 'COMPLETED',
        CANCELLED: 'CANCELLED'
    } as const;

    private _uuid: string;
    private _request: BookingRequest;
    private _bookConfirmedTime: Date;
    private _status: keyof typeof Booking.Status;

    constructor(
        uuid: string,
        request: BookingRequest,
        bookConfirmedTime: string | Date,
        status: keyof typeof Booking.Status
    ) {
        this._uuid = uuid;
        this._request = request;
        this._bookConfirmedTime = typeof bookConfirmedTime === 'string'
            ? new Date(bookConfirmedTime)
            : bookConfirmedTime;
        this._status = status;
    }

    get uuid(): string {
        return this._uuid;
    }

    get request(): BookingRequest {
        return this._request;
    }

    get bookConfirmedTime(): Date {
        return this._bookConfirmedTime;
    }

    get status(): keyof typeof Booking.Status {
        return this._status;
    }

    set status(value: keyof typeof Booking.Status) {
        this._status = value;
    }
}
