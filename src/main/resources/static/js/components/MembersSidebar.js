// components/MembersSidebar.js
import { User } from '../models/User.js';

export class MembersSidebar {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.members = [
            new User('1', '내 이름', 'online'),
            new User('2', '사용자1', 'online'),
            new User('3', '사용자2', 'offline'),
            new User('4', '사용자3', 'online')
        ];
    }

    init() {
        this.render();
        return this;
    }

    render() {
        const onlineMembers = this.members.filter(m => m.status === 'online');
        const offlineMembers = this.members.filter(m => m.status === 'offline');

        this.container.innerHTML = `
            <div class="members-category">
                온라인 — ${onlineMembers.length}
            </div>
            ${this.renderMembersList(onlineMembers)}
            
            <div class="members-category">
                오프라인 — ${offlineMembers.length}
            </div>
            ${this.renderMembersList(offlineMembers)}
        `;
    }

    renderMembersList(members) {
        return members
            .map(member => `
                <div class="member" data-member-id="${member.id}">
                    <div class="avatar">
                        ${member.getInitials()}
                        <div class="status-indicator status-${member.status}"></div>
                    </div>
                    <span class="member-info">${member.name}</span>
                </div>
            `)
            .join('');
    }

    updateMemberStatus(memberId, status) {
        const member = this.members.find(m => m.id === memberId);
        if (member) {
            member.status = status;
            this.render();
        }
    }
}