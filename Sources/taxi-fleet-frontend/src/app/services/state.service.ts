import { Injectable } from "@angular/core";
import { User } from "../model/user.model";

/*service to store various states of the logged in user.*/
@Injectable({ providedIn: 'root' })
export class StateService {
    private _currentUser!: User;

    set currentUser(user: User) {
        this._currentUser = user;
    }

    get currentUser(): User {
        return this._currentUser;
    }
}
