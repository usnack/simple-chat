
// components/UserProfile.js
import { User } from '../models/User.js';

export class UserProfile {
    constructor() {
        this.user = new User('me', '내 이름', 'online');
        this.element = document.createElement('div');
    }

    init() {
        this.render();
        return this;
    }

    render() {
        this.element.className = 'user-profile';
        this.element.innerHTML = `
            <div class="avatar">
                ${this.user.getInitials()}
                <div class="status-indicator status-${this.user.status}"></div>
            </div>
            <div class="user-info">
                <div class="user-name">${this.user.name}</div>
                <div class="user-status">
                    ${this.user.status === 'online' ? '온라인' : '오프라인'}
                </div>
            </div>
        `;
    }

    updateStatus(status) {
        this.user.status = status;
        this.render();
    }
}
