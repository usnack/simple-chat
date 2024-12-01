// models/Channel.js
export class Channel {
    constructor(id, name, type) {
        this.id = id;
        this.name = name;
        this.type = type; // 'public' | 'direct'
    }
}

