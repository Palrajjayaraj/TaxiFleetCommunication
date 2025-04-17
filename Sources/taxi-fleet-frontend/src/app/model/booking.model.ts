import { BookingRequest } from "./booking.request.model";

export class Booking {
    public static readonly Status = {
      CONFIRMED: 'CONFIRMED',
      IN_PROGRESS: 'IN_PROGRESS',
      COMPLETED: 'COMPLETED',
      CANCELLED: 'CANCELLED'
    } as const;
  
    constructor(
      public uuid: string,
      public request: BookingRequest,
      public bookConfirmedTime: Date,
      public status: keyof typeof Booking.Status
    ) {
      if (typeof bookConfirmedTime === 'string') {
        this.bookConfirmedTime = new Date(bookConfirmedTime);
      }
    }
  }