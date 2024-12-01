// components/ChannelSidebar.js
import { Channel } from '../models/Channel.js';
import { UserProfile } from './UserProfile.js';

export class ChannelSidebar {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.currentChannelId = '1';
        this.channels = {
            public: [
                new Channel('1', '일반', 'public'),
                new Channel('2', '공지사항', 'public'),
                new Channel('3', '잡담', 'public')
            ],
            direct: [
                new Channel('4', '사용자1', 'direct'),
                new Channel('5', '사용자2', 'direct'),
                new Channel('6', '사용자3', 'direct')
            ]
        };
    }

    init() {
        this.render();
        this.attachEventListeners();
        return this;
    }

    render() {
        this.container.innerHTML = `
            <div class="channels-list">
                <div class="channel-category">공개 채널</div>
                ${this.renderPublicChannels()}
                <div class="channel-category">다이렉트 메시지</div>
                ${this.renderDirectChannels()}
            </div>
        `;

        // 프로필 추가
        const userProfile = new UserProfile().init();
        this.container.appendChild(userProfile.element);
    }

    renderPublicChannels() {
        return this.channels.public
            .map(channel => `
                <div class="channel ${this.currentChannelId === channel.id ? 'active' : ''}" 
                     data-channel-id="${channel.id}" 
                     data-channel-type="public">
                    # ${channel.name}
                </div>
            `).join('');
    }

    renderDirectChannels() {
        return this.channels.direct
            .map(channel => `
                <div class="dm-channel ${this.currentChannelId === channel.id ? 'active' : ''}"
                     data-channel-id="${channel.id}"
                     data-channel-type="direct">
                    <div class="avatar">
                        ${channel.name[0].toUpperCase()}
                        <div class="status-indicator status-online"></div>
                    </div>
                    <span class="member-info">${channel.name}</span>
                </div>
            `).join('');
    }

    attachEventListeners() {
        this.container.addEventListener('click', (e) => {
            const channelElement = e.target.closest('.channel, .dm-channel');
            if (!channelElement) return;

            const prevActive = this.container.querySelector('.active');
            if (prevActive) prevActive.classList.remove('active');
            channelElement.classList.add('active');

            const channelId = channelElement.dataset.channelId;
            const channelType = channelElement.dataset.channelType;
            this.currentChannelId = channelId;

            window.dispatchEvent(new CustomEvent('channelChange', {
                detail: this.getChannelById(channelId)
            }));
        });
    }

    getChannelById(channelId) {
        return [...this.channels.public, ...this.channels.direct]
            .find(channel => channel.id === channelId);
    }
}