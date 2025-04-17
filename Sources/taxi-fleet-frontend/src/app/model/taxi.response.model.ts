import { TaxiResponse } from "./taxi.model";
/**The response form Taxi to the server */
export class TaxiResponsePayload {
    constructor(
        private taxiID: string,
        private requestID: string,
        private response: TaxiResponse
    ) { }

    getTaxiID(): string {
        return this.taxiID;
    }

    setTaxiID(value: string): void {
        this.taxiID = value;
    }

    getRequestID(): string {
        return this.requestID;
    }

    setRequestID(value: string): void {
        this.requestID = value;
    }

    getResponse(): TaxiResponse {
        return this.response;
    }

    setResponse(value: TaxiResponse): void {
        this.response = value;
    }

}
