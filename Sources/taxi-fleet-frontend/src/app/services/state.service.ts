import { Injectable } from "@angular/core";
import { Role } from "../model/role.model";
import { User } from "../model/user.model";
import { INamedEntity } from "../model/readable.model";

/*service to store various states of the logged in user.*/
@Injectable({ providedIn: 'root' })
export class StateService {
    private _currentRole!: Role;
    private _currentEntity!: INamedEntity;

    /** Call once you’ve got both the role and its selected entity */
    setState(role: Role, entity: INamedEntity) {
        this._currentRole = role;
        this._currentEntity = entity;
    }

    get currentRole(): Role {
        return this._currentRole;
    }
    get currentEntity(): INamedEntity {
        return this._currentEntity;
    }
}