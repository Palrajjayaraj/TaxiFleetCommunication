export interface BookingTrend {
    date: string;
    count: number;
}

export interface BookingRequestsConversion {
    date: Date, requestCount: number, bookingCount: number;
}