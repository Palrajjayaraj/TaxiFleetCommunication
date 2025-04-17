import { HttpClient } from '@angular/common/http';
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
export class CommonService {

  private commonServerURL: string = environment.BASE_URL + 'common/';

  private locationsURL: string = this.commonServerURL + 'locations';

  private usersURL: string = this.commonServerURL + 'users';

  private taxiURL: string = this.commonServerURL + 'taxis';

  private locations?: Observable<Location[]>;

  private users?: Observable<User[]>;

  private taxis?: Observable<Taxi[]>;

  constructor(private http: HttpClient) { }


  getLocations(): Observable<Location[]> {
    if (!this.locations) {
      this.locations = this.http.get<Location[]>(this.locationsURL).pipe(
        map(arr => arr.map(loc => new Location(loc.id, loc.readableName))),
        shareReplay(1) // cache the response
      );
    }
    return this.locations;
  }

  /** Fetch users and cache them */
  getUsers(): Observable<User[]> {
    if (!this.users) {
      this.users = this.http.get<User[]>(this.usersURL).pipe(
        map(arr => arr.map(user => new User(user.uuid, user.name))),
        shareReplay(1) // cache the response
      );
    }
    return this.users;
  }

  /** Fetch users and cache them */
  getTaxis(): Observable<Taxi[]> {
    if (!this.taxis) {
      this.taxis = this.http.get<Taxi[]>(this.taxiURL).pipe(
        map(arr => arr.map(taxi => new Taxi(taxi.id, taxi.numberPlate, taxi.status, taxi.currentLocation))),
        shareReplay(1) // cache the response
      );
    }
    return this.taxis;
  }
}
