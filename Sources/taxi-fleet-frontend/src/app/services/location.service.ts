import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Location } from '../model/location.model';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private locationURL: string = environment.BASE_URL + 'locations';

  private initialized: boolean = false;

  private locations: Location[] = [];

  constructor(private http: HttpClient) { }

  /**  Fetch locations from the backend and map them to Location objects*/
  getLocations(): Location[] {
    if (this.initialized) {
      return this.locations;
    }
    if (!this.initialized) {
      this.http.get<Location[]>(this.locationURL).pipe(
        map(locations => locations.map(location => new Location(location.id, location.readableName)))
      ).subscribe(locs => {
        locs.forEach(l => this.locations.push(l));
        this.initialized = true;
      });
    }
    return this.locations;
  }
}
