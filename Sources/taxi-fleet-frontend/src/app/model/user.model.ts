import { INamedEntity } from "./readable.model";

/**
 * User pojo model
 */
export class User implements INamedEntity {
    uuid: string;
    name: string;
    constructor(
        uuid: string,
        name: string
    ) {
        this.uuid = uuid;
        this.name = name;
    }
    get readAbleName(): string {
        return this.name;
    }
}
