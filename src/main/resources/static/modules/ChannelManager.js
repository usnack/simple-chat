// src/modules/ChannelManager.js
import { api } from '../utils/api.js';

export class ChannelManager {
    constructor(currentUserId) {
        this.currentUserId = currentUserId; // 현재 로그인한 사용자 ID
        this.channels = [];
        this.initializeEventListeners();
        this.loadChannels();
    }

    async loadChannels() {
        try {
            const channels = await api.get('/channels');
            this.channels = channels;
            this.renderChannels();
        } catch (error) {
            console.error('Error loading channels:', error);
        }
    }

    renderChannels() {
        const groupMessagesList = document.getElementById('groupMessagesList');
        const directMessagesList = document.getElementById('directMessagesList');

        // Clear existing lists
        groupMessagesList.innerHTML = '';
        directMessagesList.innerHTML = '';

        this.channels.forEach(channel => {
            const channelElement = document.createElement('div');
            channelElement.className = 'channel-item';
            channelElement.dataset.channelId = channel.id;

            if (channel.type === 'GROUP') {
                channelElement.innerHTML = `
                    <i class="fas fa-hashtag"></i>
                    ${channel.name}
                `;
                groupMessagesList.appendChild(channelElement);
            } else {
                // For DM channels, we'll want to show the other user's name
                channelElement.innerHTML = `
                    <div class="channel-avatar" style="background-color: #7289da">U</div>
                    ${channel.name}
                `;
                directMessagesList.appendChild(channelElement);
            }

            channelElement.addEventListener('click', () => this.selectChannel(channel.id));
        });
    }

    initializeEventListeners() {
        // Category toggle events
        document.querySelectorAll('.channel-category').forEach(category => {
            category.addEventListener('click', (e) => {
                if (!e.target.closest('.add-group-channel')) {
                    this.toggleCategory(category);
                }
            });
        });

        // Channel creation modal events
        const modal = document.getElementById('channelModal');
        const channelNameInput = document.getElementById('channelName');
        const createButton = document.getElementById('createButton');
        const cancelButton = document.getElementById('cancelButton');

        // Show modal when add channel button is clicked
        document.querySelectorAll('.add-group-channel').forEach(button => {
            button.addEventListener('click', (e) => {
                e.stopPropagation();
                modal.style.display = 'block';
                channelNameInput.focus();
            });
        });

        // Handle modal close
        cancelButton.addEventListener('click', () => this.closeModal());
        modal.addEventListener('click', (e) => {
            if (e.target === modal) this.closeModal();
        });

        // Handle channel name input
        channelNameInput.addEventListener('input', () => {
            createButton.disabled = !channelNameInput.value.trim();
        });

        // Handle channel creation
        createButton.addEventListener('click', () => this.createChannel());
    }

    toggleCategory(categoryElement) {
        const arrow = categoryElement.querySelector('.category-arrow');
        const channelGroup = categoryElement.nextElementSibling;

        arrow.classList.toggle('collapsed');
        channelGroup.classList.toggle('collapsed');
    }

    closeModal() {
        const modal = document.getElementById('channelModal');
        const channelNameInput = document.getElementById('channelName');
        const createButton = document.getElementById('createButton');

        modal.style.display = 'none';
        channelNameInput.value = '';
        createButton.disabled = true;
    }

    async createChannel() {
        const channelName = document.getElementById('channelName').value.trim();
        const channelTopic = document.getElementById('channelTopic').value.trim();

        if (!channelName) return;

        try {
            const newChannel = await api.post('/channels', {
                type: 'GROUP',
                name: channelName,
                description: channelTopic,
                ownerId: this.currentUserId // 채널 생성자를 owner로 설정
            });

            this.channels.push(newChannel);
            this.renderChannels();
            this.closeModal();
            this.selectChannel(newChannel.id);
        } catch (error) {
            console.error('Error creating channel:', error);
        }
    }

    selectChannel(channelId) {
        // Remove active class from all channels
        document.querySelectorAll('.channel-item').forEach(item => {
            item.classList.remove('active');
        });

        // Add active class to selected channel
        const selectedChannel = document.querySelector(`[data-channel-id="${channelId}"]`);
        if (selectedChannel) {
            selectedChannel.classList.add('active');
        }

        // Update chat header with channel info
        const channel = this.channels.find(c => c.id === channelId);
        if (channel) {
            const chatHeader = document.querySelector('.chat-header');
            chatHeader.innerHTML = `
                <i class="fas fa-${channel.type === 'GROUP' ? 'hashtag' : 'user'}"></i>
                <span style="margin-left: 8px;">${channel.name}</span>
            `;
        }

        // Dispatch event for other modules
        document.dispatchEvent(new CustomEvent('channelSelected', {
            detail: { channelId }
        }));
    }
}