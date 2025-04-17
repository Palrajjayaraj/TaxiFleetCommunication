import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { INamedEntity } from '../model/readable.model';
import { Role } from '../model/role.model';
import { Taxi } from '../model/taxi.model';
import { User } from '../model/user.model';
import { CommonService } from '../services/common.service';
import { StateService } from '../services/state.service';

/**
 * handles various roles in the application
 * @param T The real user type being handled.
 */
export interface RoleHandler<T extends INamedEntity> {

    readonly role: Role;

    getAllRoleData(): Observable<T[]>;

    onSelect(entity: T): void;

    getNextPageName(): string;

    getDefaultUser(usersList: T[]): T;
}

@Injectable({ providedIn: 'root' })
export class UserRoleHandler implements RoleHandler<User> {
    readonly role: Role = Role.User;

    constructor(private userService: CommonService, private stateService: StateService) { }
    getDefaultUser(usersList: User[]): User {
        // Try to find Palraj Jayaraj in the list
        const user = usersList.find(u => u.name === 'Palraj Jayaraj');
        if (!user) {
            // return the first element in the list, if Palraj is not there.
            return usersList[0];
        }
        return user;
    }

    getAllRoleData(): Observable<User[]> {
        return this.userService.getUsers();
    }

    onSelect(user: User): void {
        this.stateService.setState(this.role, user);
    }

    getNextPageName(): string {
        return '/user-dashboard';
    }
}

@Injectable({ providedIn: 'root' })
export class TaxiRoleHandler implements RoleHandler<Taxi> {
    readonly role = Role.Taxi;

    constructor(private taxiService: CommonService, private stateService: StateService) { }
    
    getDefaultUser(taxis: Taxi[]): Taxi {
        return taxis[0];
    }

    getAllRoleData(): Observable<Taxi[]> {
        return this.taxiService.getTaxis();
    }

    onSelect(taxi: Taxi): void {
        this.stateService.setState(this.role, taxi);
    }

    getNextPageName(): string {
        return '/taxi-dashboard';
    }
}

@Injectable({
    providedIn: 'root'
})
export class RoleHandlerFactory {
    private handlerMap = new Map<Role, RoleHandler<any>>();

    constructor(private injector: Injector) {
        this.handlerMap.set(Role.User, this.injector.get(UserRoleHandler));
        this.handlerMap.set(Role.Taxi, this.injector.get(TaxiRoleHandler));
        // this.handlerMap.set(Role.Admin, this.injector.get(AdminRoleHandler));
    }

    /**
     * Returns the handler for the given role
     */
    getHandler(role: Role): RoleHandler<INamedEntity> {
        const handler = this.handlerMap.get(role);
        if (!handler) {
            throw new Error(`No RoleHandler registered for role: ${role}`);
        }
        return handler as RoleHandler<INamedEntity>;
    }
}
