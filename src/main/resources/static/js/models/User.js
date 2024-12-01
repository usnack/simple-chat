export class User {
    constructor(id, name, status = 'offline') {
        this.id = id;
        this.name = name;
        this.status = status; // 'online' | 'offline'
    }

    getInitials() {
        return this.name[0].toUpperCase();
    }
}