/**
 * User pojo model
 */
export class User {
    private readonly _uuid: string;
    private readonly _name: string;

    constructor(uuid: string, name: string) {
        this._uuid = uuid;
        this._name = name;
    }

    get uuid(): string {
        return this._uuid;
    }

    get name(): string {
        return this._name;
    }
}