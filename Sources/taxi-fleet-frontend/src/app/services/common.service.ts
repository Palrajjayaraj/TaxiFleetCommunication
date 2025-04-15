import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, shareReplay } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Location } from '../model/location.model';
import { User } from '../model/user.model';
import { Observable } from 'rxjs/internal/Observable';

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

  private locations?: Observable<Location[]>;

  private users?: Observable<User[]>;

  constructor(private http: HttpClient) { }


  getLocations(): Observable<Location[]> {
    if (!this.locations) {
      this.locations = this.http.get<Location[]>(this.locationsURL).pipe(
        map(locations => locations.map(loc => new Location(loc.id, loc.readableName))),
        shareReplay(1) // cache the response
      );
    }
    return this.locations;
  }

  /** Fetch users and cache them */
  getUsers(): Observable<User[]> {
    if (!this.users) {
      this.users = this.http.get<User[]>(this.usersURL).pipe(
        map(users => users.map(u => new User(u.uuid, u.name))),
        shareReplay(1) // cache the response
      );
    }
    return this.users;
  }
}
