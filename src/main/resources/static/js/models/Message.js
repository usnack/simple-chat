export class Message {
    constructor(id, author, content) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
    }

    getFormattedTime() {
        return this.timestamp.toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit'
        });
    }
}