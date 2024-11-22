// src/modules/MessageManager.js
import { api } from '../utils/api.js';

export class MessageManager {
    constructor(currentUserId) {
        this.currentUserId = currentUserId;
        this.currentChannelId = null;
        this.messages = [];
        this.currentPage = 0;
        this.hasMore = false;
        this.isLoading = false;
        this.lastMessageDate = null;

        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // 채널 선택 이벤트 리스닝
        document.addEventListener('channelSelected', (event) => {
            this.currentChannelId = event.detail.channelId;
            this.resetState();
            this.loadMessages();
        });

        // 메시지 입력 처리
        const messageInput = document.getElementById('messageInput');
        messageInput.addEventListener('keypress', async (e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                const content = messageInput.value.trim();
                if (content && this.currentChannelId) {
                    await this.sendMessage(content);
                    messageInput.value = '';
                }
            }
        });

        // 무한 스크롤을 위한 스크롤 이벤트 처리
        const chatMessages = document.getElementById('chatMessages');
        chatMessages.addEventListener('scroll', () => {
            if (!this.isLoading && this.hasMore && chatMessages.scrollTop === 0) {
                this.loadMoreMessages();
            }
        });
    }

    resetState() {
        this.messages = [];
        this.currentPage = 0;
        this.hasMore = false;
        this.isLoading = false;
        this.lastMessageDate = null;
        const messagesContainer = document.getElementById('messagesContainer');
        messagesContainer.innerHTML = '';
    }

    async loadMessages() {
        if (!this.currentChannelId) return;

        try {
            this.isLoading = true;
            const response = await api.get(`/messages?channelId=${this.currentChannelId}&page=0&size=50`);

            this.messages = response.data;
            this.hasMore = response.hasMore;
            this.currentPage = response.nextPage;

            this.renderMessages();
            this.scrollToBottom();
        } catch (error) {
            console.error('Error loading messages:', error);
        } finally {
            this.isLoading = false;
        }
    }

    async loadMoreMessages() {
        if (!this.currentChannelId || this.isLoading || !this.hasMore) return;

        try {
            this.isLoading = true;
            const response = await api.get(
                `/messages?channelId=${this.currentChannelId}&page=${this.currentPage}&size=50`
            );

            // 스크롤 위치 유지를 위해 이전 높이 저장
            const chatMessages = document.getElementById('chatMessages');
            const oldScrollHeight = chatMessages.scrollHeight;

            // 새 메시지를 앞쪽에 추가
            this.messages = [...response.data, ...this.messages];
            this.hasMore = response.hasMore;
            this.currentPage = response.nextPage;

            this.renderMessages();

            // 스크롤 위치 복원
            chatMessages.scrollTop = chatMessages.scrollHeight - oldScrollHeight;
        } catch (error) {
            console.error('Error loading more messages:', error);
        } finally {
            this.isLoading = false;
        }
    }

    async sendMessage(content) {
        try {
            const response = await api.post('/messages', {
                content,
                authorId: this.currentUserId,
                channelId: this.currentChannelId
            });

            // 새 메시지를 목록에 추가하고 화면 갱신
            this.messages.push(response);
            this.renderMessages();
            this.scrollToBottom();
        } catch (error) {
            console.error('Error sending message:', error);
        }
    }

    renderMessages() {
        const messagesContainer = document.getElementById('messagesContainer');
        const messageGroups = this.groupMessagesByDate();

        let html = '';

        messageGroups.forEach(group => {
            // 날짜 구분선 추가
            if (this.shouldShowDateDivider(group.date)) {
                html += this.renderDateDivider(group.date);
                this.lastMessageDate = group.date;
            }

            // 메시지 그룹 렌더링
            group.messages.forEach(message => {
                html += this.renderMessage(message);
            });
        });

        messagesContainer.innerHTML = html;
        this.attachMessageEventListeners();
    }

    groupMessagesByDate() {
        const groups = [];
        let currentGroup = null;

        this.messages.forEach(message => {
            const messageDate = new Date(message.createdAt);
            const dateStr = messageDate.toLocaleDateString();

            if (!currentGroup || currentGroup.date !== dateStr) {
                currentGroup = {
                    date: dateStr,
                    messages: []
                };
                groups.push(currentGroup);
            }

            currentGroup.messages.push(message);
        });

        return groups;
    }

    shouldShowDateDivider(date) {
        return this.lastMessageDate !== date;
    }

    renderDateDivider(date) {
        const messageDate = new Date(date);
        const today = new Date();
        const yesterday = new Date(today);
        yesterday.setDate(yesterday.getDate() - 1);

        let dateText;
        if (messageDate.toDateString() === today.toDateString()) {
            dateText = '오늘';
        } else if (messageDate.toDateString() === yesterday.toDateString()) {
            dateText = '어제';
        } else {
            dateText = messageDate.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                weekday: 'long'
            });
        }

        return `
            <div class="message-date-divider">
                <span class="message-date">${dateText}</span>
            </div>
        `;
    }

    renderMessage(message) {
        const messageDate = new Date(message.createdAt);
        const timeStr = messageDate.toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit'
        });

        const isCurrentUser = message.author.id === this.currentUserId;

        return `
            <div class="message" data-message-id="${message.id}">
                <div class="message-avatar" style="background-image: url('${message.author.avatarUrl || '/api/placeholder/40/40'}')"></div>
                <div class="message-content">
                    <div class="message-header">
                        <span class="message-author">${message.author.username}</span>
                        <span class="message-timestamp">Today at ${timeStr}</span>
                    </div>
                    <div class="message-text">${this.formatMessageContent(message.content)}</div>
                </div>
                ${isCurrentUser ? `
                    <div class="message-actions">
                        <button class="message-edit-btn">
                            <i class="fas fa-pencil"></i>
                        </button>
                        <button class="message-delete-btn">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                ` : ''}
            </div>
        `;
    }

    formatMessageContent(content) {
        // URL을 링크로 변환
        content = content.replace(
            /(https?:\/\/[^\s<]+[^<.,:;"')\]\s])/g,
            '<a href="$1" target="_blank">$1</a>'
        );

        // 줄바꿈 처리
        content = content.replace(/\n/g, '<br>');

        return content;
    }

    attachMessageEventListeners() {
        // 메시지 편집 버튼
        document.querySelectorAll('.message-edit-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const messageElement = e.target.closest('.message');
                const messageId = messageElement.dataset.messageId;
                this.startEditingMessage(messageId);
            });
        });

        // 메시지 삭제 버튼
        document.querySelectorAll('.message-delete-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const messageElement = e.target.closest('.message');
                const messageId = messageElement.dataset.messageId;
                await this.deleteMessage(messageId);
            });
        });
    }

    async startEditingMessage(messageId) {
        const messageElement = document.querySelector(`[data-message-id="${messageId}"]`);
        const messageTextElement = messageElement.querySelector('.message-text');
        const originalContent = messageTextElement.textContent;

        // 편집 폼으로 변경
        messageTextElement.innerHTML = `
            <div class="message-edit-form">
                <textarea class="message-edit-input">${originalContent}</textarea>
                <div class="message-edit-actions">
                    <button class="message-edit-save">Save</button>
                    <button class="message-edit-cancel">Cancel</button>
                </div>
            </div>
        `;

        const textarea = messageTextElement.querySelector('.message-edit-input');
        textarea.focus();

        // 취소 버튼
        messageTextElement.querySelector('.message-edit-cancel').addEventListener('click', () => {
            messageTextElement.innerHTML = this.formatMessageContent(originalContent);
        });

        // 저장 버튼
        messageTextElement.querySelector('.message-edit-save').addEventListener('click', async () => {
            const newContent = textarea.value.trim();
            if (newContent && newContent !== originalContent) {
                await this.updateMessage(messageId, newContent);
            } else {
                messageTextElement.innerHTML = this.formatMessageContent(originalContent);
            }
        });
    }

    async updateMessage(messageId, content) {
        try {
            const response = await api.patch('/messages', {
                messageId,
                content
            });

            // 메시지 목록 업데이트
            const index = this.messages.findIndex(m => m.id === messageId);
            if (index !== -1) {
                this.messages[index] = response;
                this.renderMessages();
            }
        } catch (error) {
            console.error('Error updating message:', error);
        }
    }

    async deleteMessage(messageId) {
        if (!confirm('정말로 이 메시지를 삭제하시겠습니까?')) return;

        try {
            await api.delete(`/messages/${messageId}`);

            // 메시지 목록에서 제거
            this.messages = this.messages.filter(m => m.id !== messageId);
            this.renderMessages();
        } catch (error) {
            console.error('Error deleting message:', error);
        }
    }

    scrollToBottom() {
        const chatMessages = document.getElementById('chatMessages');
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
}