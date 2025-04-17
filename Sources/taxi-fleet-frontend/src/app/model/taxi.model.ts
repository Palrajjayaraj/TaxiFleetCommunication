import { Location } from './location.model';
import { INamedEntity } from './readable.model';

export enum TaxiStatus {
    AVAILABLE = 'AVAILABLE',
    BOOKED = 'BOOKED',
    RIDING = 'RIDING',
    OFFLINE = 'OFFLINE'
}

export enum TaxiResponse {
    ACCEPTED = 'ACCEPTED',
    REJECTED = 'REJECTED'
}

export class Taxi implements INamedEntity{
    id: string;
    numberPlate: string;
    status: TaxiStatus;
    currentLocation: Location;

    constructor(
        id: string,
        numberPlate: string,
        status: TaxiStatus,
        currentLocation: Location
    ) {
        this.id = id;
        this.numberPlate = numberPlate;
        this.status = status;
        this.currentLocation = currentLocation;
    }
    get readAbleName(): string {
        return this.numberPlate;
    }

}
