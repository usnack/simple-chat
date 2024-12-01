// components/ChannelSidebar.js
import { Channel } from '../models/Channel.js';
import { UserProfile } from './UserProfile.js';

export class ChannelSidebar {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.currentChannelId = '1';
        this.serverName = '심플챗';
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
            <div class="server-label">
                <span class="server-name">${this.serverName}</span>
                <span class="server-expand">⌵</span>
            </div>
            <div class="channels-list">
                <div class="channel-category">
                    <div class="category-header">
                        <span>공개 채널</span>
                        <span class="category-icon" title="채널 추가">+</span>
                    </div>
                </div>
                ${this.renderPublicChannels()}
                <div class="channel-category">
                    <div class="category-header">
                        <span>다이렉트 메시지</span>
                        <span class="category-icon" title="새 메시지">+</span>
                    </div>
                </div>
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
                    <span class="channel-name"># ${channel.name}</span>
                    <span class="channel-settings" title="채널 설정">⚙️</span>
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
                    <span class="dm-delete" title="대화 삭제">✕</span>
                </div>
            `).join('');
    }

    attachEventListeners() {
        this.container.addEventListener('click', (e) => {
            // 채널 선택 이벤트
            const channelElement = e.target.closest('.channel, .dm-channel');
            if (channelElement && !e.target.matches('.channel-settings, .dm-delete')) {
                const prevActive = this.container.querySelector('.active');
                if (prevActive) prevActive.classList.remove('active');
                channelElement.classList.add('active');

                const channelId = channelElement.dataset.channelId;
                this.currentChannelId = channelId;

                window.dispatchEvent(new CustomEvent('channelChange', {
                    detail: this.getChannelById(channelId)
                }));
            }

            // 채널 설정 버튼 이벤트
            if (e.target.matches('.channel-settings')) {
                const channelId = e.target.closest('.channel').dataset.channelId;
                this.handleChannelSettings(channelId);
                e.stopPropagation();
            }

            // DM 삭제 버튼 이벤트
            if (e.target.matches('.dm-delete')) {
                const channelId = e.target.closest('.dm-channel').dataset.channelId;
                this.handleDMDelete(channelId);
                e.stopPropagation();
            }

            // 채널 추가 버튼 이벤트
            if (e.target.matches('.category-icon')) {
                const isPublic = e.target.closest('.channel-category').textContent.includes('공개 채널');
                this.handleAddChannel(isPublic);
                e.stopPropagation();
            }
        });
    }

    handleChannelSettings(channelId) {
        console.log('채널 설정 열기:', channelId);
        // TODO: 채널 설정 모달 구현
    }

    handleDMDelete(channelId) {
        console.log('DM 채널 삭제:', channelId);
        // TODO: 삭제 확인 모달 구현
    }

    handleAddChannel(isPublic) {
        console.log(isPublic ? '공개 채널 추가' : '새 메시지 보내기');
        // TODO: 채널 추가 또는 새 메시지 모달 구현
    }

    getChannelById(channelId) {
        return [...this.channels.public, ...this.channels.direct]
            .find(channel => channel.id === channelId);
    }
}