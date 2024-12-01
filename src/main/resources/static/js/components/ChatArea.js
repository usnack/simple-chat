
// components/ChatArea.js
import { Message } from '../models/Message.js';

export class ChatArea {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.messages = [];
        this.currentChannel = null;
        this.chatInput = null; // 입력창 요소 참조 저장
    }

    init() {
        this.render();
        this.attachEventListeners();
        this.listenToChannelChanges();
        return this;
    }

    render() {
        if (!this.currentChannel) return;

        this.container.innerHTML = `
            <div class="chat-header">
                ${this.currentChannel.type === 'public' ? '#' : '@'} 
                ${this.currentChannel.name}
            </div>
            <div class="chat-messages">
                ${this.renderMessages()}
            </div>
            <div class="chat-input">
                <input type="text" placeholder="${this.getInputPlaceholder()}">
            </div>
        `;
        // 입력창 요소 참조 저장
        this.chatInput = this.container.querySelector('.chat-input input');
        // 초기 포커스 설정
        this.chatInput.focus();
    }

    renderMessages() {
        return this.messages
            .map(message => `
                <div class="message">
                    <div class="avatar">${message.author[0].toUpperCase()}</div>
                    <div class="message-content">
                        <div class="message-header">
                            <span class="message-author">${message.author}</span>
                            <span class="message-time">${message.getFormattedTime()}</span>
                        </div>
                        <div class="message-text">${message.content}</div>
                    </div>
                </div>
            `)
            .join('');
    }

    getInputPlaceholder() {
        const prefix = this.currentChannel.type === 'public' ? '#' : '@';
        return `${prefix}${this.currentChannel.name} 채널에 메시지 보내기`;
    }

    attachEventListeners() {
        this.container.addEventListener('keypress', (e) => {
            if (e.target.tagName !== 'INPUT') return;
            if (e.key !== 'Enter' || !e.target.value.trim()) return;

            const message = new Message(
                Date.now().toString(),
                '내 이름',
                e.target.value.trim()
            );

            this.addMessage(message);
            e.target.value = '';

            this.chatInput.focus();
        });
    }

    listenToChannelChanges() {
        window.addEventListener('channelChange', (e) => {
            this.currentChannel = e.detail;
            this.messages = []; // 채널 변경 시 메시지 초기화
            this.render();
        });
    }

    addMessage(message) {
        this.messages.unshift(message);
        this.render();
    }
}
