import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { map, shareReplay } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Location } from '../model/location.model';
import { Taxi } from '../model/taxi.model';
import { User } from '../model/user.model';

/**
 * Service to connect to the common service in the backend server.
 */
@Injectable({
    providedIn: 'root'
})
export class TaxiService {

    private taxiServerURL: string = environment.BASE_URL + 'taxi/';

    private updateStateURL: string = this.taxiServerURL + 'updateState';

    constructor(private http: HttpClient) { }

    /**Seds requests to the server to update the given taxi's status, */
    updateTaxiState(taxi: Taxi): void {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        this.http.post<Taxi>(this.updateStateURL, taxi, { headers }).subscribe(response => console.log('Success'), error => console.error(error));;
    }

}
