export default () => {
    // 채팅 기능 구현
    const messageInput = document.getElementById('messageInput');
    const messagesContainer = document.getElementById('messagesContainer');
    const chatMessages = document.getElementById('chatMessages');

// 메시지 전송 함수
    function sendMessage(text, isInitial = false) {
        if (!text.trim()) return;

        const messageDiv = document.createElement('div');
        messageDiv.className = 'message';

        const timestamp = new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});

        messageDiv.innerHTML = `
                <div class="message-avatar"></div>
                <div class="message-content">
                    <div class="message-header">
                        <span class="message-author">${isInitial ? 'Discord Bot' : 'User'}</span>
                        <span class="message-timestamp">Today at ${timestamp}</span>
                    </div>
                    <div class="message-text">${text}</div>
                </div>
            `;

        messagesContainer.appendChild(messageDiv);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

// Enter 키 이벤트 리스너
    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage(messageInput.value);
            messageInput.value = '';
        }
    });

// 채널 카테고리 접기/펼치기 기능
    const groupMessagesCategory = document.getElementById('groupMessagesCategory');
    const directMessagesCategory = document.getElementById('directMessagesCategory');
    const groupMessagesList = document.getElementById('groupMessagesList');
    const directMessagesList = document.getElementById('directMessagesList');

    function toggleCategory(categoryHeader, categoryList) {
        const arrow = categoryHeader.querySelector('.category-arrow');
        arrow.classList.toggle('collapsed');
        categoryList.classList.toggle('collapsed');
    }

    groupMessagesCategory.addEventListener('click', (e) => {
        if (!e.target.closest('.add-group-channel')) {
            toggleCategory(groupMessagesCategory, groupMessagesList);
        }
    });

    directMessagesCategory.addEventListener('click', (e) => {
        if (!e.target.closest('.add-group-channel')) {
            toggleCategory(directMessagesCategory, directMessagesList);
        }
    });

// 초기 메시지 추가
    const initialMessages = [
        "Welcome to Discodeit!",
        "You can send direct messages to users or communicate in group chats.",
        "Try sending a message by pressing Enter."
    ];

// 시간 순서대로 메시지 추가
    initialMessages.forEach(msg => {
        sendMessage(msg, true);
    });
}