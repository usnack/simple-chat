// DOM 요소 선택
const messageInput = document.querySelector('.message-input');
const sendButton = document.querySelector('.send-button');
const messagesContainer = document.querySelector('.messages-container');
const channels = document.querySelectorAll('.channel, .dm-channel');
const categories = document.querySelectorAll('.category');
const addDmButton = document.querySelector('.add-dm-button');
const addChannelButton = document.querySelector('.add-channel-button');

// 카테고리 토글 기능 초기화
categories.forEach(category => {
    const header = category.querySelector('.category-header');
    const channelList = category.querySelector('.channel-list');

    // 채널 리스트의 초기 높이 설정
    channelList.style.maxHeight = channelList.scrollHeight + 'px';

    header.addEventListener('click', (e) => {
        // + 버튼 클릭 시 카테고리 토글 방지
        if (e.target.classList.contains('add-dm-button') ||
            e.target.classList.contains('add-channel-button')) {
            e.stopPropagation();
            return;
        }

        category.classList.toggle('collapsed');

        // collapsed 상태에 따라 max-height 토글
        if (category.classList.contains('collapsed')) {
            channelList.style.maxHeight = '0';
        } else {
            channelList.style.maxHeight = channelList.scrollHeight + 'px';
        }
    });
});

// 채널 추가 버튼 클릭 이벤트
addChannelButton.addEventListener('click', () => {
    // TODO: 채널 추가 모달 표시
    console.log('새 채널 추가 버튼 클릭됨');
});

// DM 추가 버튼 클릭 이벤트
addDmButton.addEventListener('click', () => {
    // TODO: DM 추가 모달 표시
    console.log('새 DM 추가 버튼 클릭됨');
});

// 메시지 생성 함수
function createMessageElement(message) {
    const messageDiv = document.createElement('div');
    messageDiv.className = 'message';
    messageDiv.innerHTML = `
        <div class="message-content">
            <div class="message-avatar">
                <div class="user-avatar small">${message.username.charAt(0)}</div>
            </div>
            <div class="message-details">
                <div class="message-header">
                    <span class="message-username">${message.username}</span>
                    <span class="message-time">${formatTimestamp(message.timestamp)}</span>
                </div>
                <div class="message-text">${message.content}</div>
            </div>
        </div>
    `;
    return messageDiv;
}

// 메시지 전송 함수
function sendMessage() {
    const messageText = messageInput.value.trim();
    if (messageText === '') return;

    const messageElement = createMessageElement({
        content: messageText,
        username: '사용자명',
        timestamp: new Date()
    });

    // 메시지를 컨테이너의 시작 부분에 추가 (최신 메시지가 아래에 표시)
    if (messagesContainer.firstChild) {
        messagesContainer.insertBefore(messageElement, messagesContainer.firstChild);
    } else {
        messagesContainer.appendChild(messageElement);
    }

    // 입력창 초기화
    messageInput.value = '';
}

// 타임스탬프 포맷팅 함수
function formatTimestamp(date) {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const messageDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());

    const timeOptions = { hour: '2-digit', minute: '2-digit' };
    const dateOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    };

    // 같은 날짜인 경우 시간만 표시
    if (messageDate.getTime() === today.getTime()) {
        return new Intl.DateTimeFormat('ko-KR', timeOptions).format(date);
    }

    // 다른 날짜인 경우 전체 날짜와 시간 표시
    return new Intl.DateTimeFormat('ko-KR', dateOptions).format(date);
}

// 메시지 전송 이벤트 리스너
sendButton.addEventListener('click', sendMessage);

messageInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
});

// 채널 선택 기능
channels.forEach(channel => {
    channel.addEventListener('click', () => {
        // 기존 활성 채널에서 active 클래스 제거
        document.querySelector('.channel.active, .dm-channel.active')?.classList.remove('active');

        // 클릭된 채널에 active 클래스 추가
        channel.classList.add('active');

        // 채팅 헤더 업데이트
        updateChatHeader(channel);

        // 채널 변경 시 메시지 목록 초기화 및 환영 메시지 표시
        resetMessagesContainer(channel);
    });
});

// 채팅 헤더 업데이트 함수
function updateChatHeader(channel) {
    const headerChannelName = document.querySelector('.chat-header .channel-name');
    const headerChannelTopic = document.querySelector('.chat-header .channel-topic');
    const channelName = channel.querySelector('.channel-name').textContent;

    // DM 채널과 일반 채널 구분하여 헤더 업데이트
    if (channel.classList.contains('dm-channel')) {
        headerChannelName.textContent = channelName;
        headerChannelTopic.textContent = '다이렉트 메시지';
    } else {
        headerChannelName.textContent = channelName;
        headerChannelTopic.textContent = '채널 주제';
    }
}

// 메시지 컨테이너 초기화 및 환영 메시지 표시 함수
function resetMessagesContainer(channel) {
    const channelName = channel.querySelector('.channel-name').textContent.trim();
    messagesContainer.innerHTML = '';

    // 일반 채널인 경우에만 환영 메시지 표시
    if (!channel.classList.contains('dm-channel')) {
        const welcomeMessage = `
            <div class="welcome-message">
                <div class="channel-icon">#</div>
                <h2 class="welcome-title">${channelName}에 오신 걸 환영합니다!</h2>
                <p class="welcome-description">${channelName} 채널의 시작이에요.</p>
                <div class="channel-actions">
                    <a href="#" class="channel-action">
                        <span class="action-icon">✏️</span>
                        채널 편집
                    </a>
                </div>
            </div>
        `;
        messagesContainer.innerHTML = welcomeMessage;
    }

    // 현재 날짜 구분선 추가
    const dateDivider = document.createElement('div');
    dateDivider.className = 'date-divider';
    dateDivider.innerHTML = `<span class="date">${formatDateDivider(new Date())}</span>`;

    if (messagesContainer.firstChild) {
        messagesContainer.insertBefore(dateDivider, messagesContainer.firstChild);
    } else {
        messagesContainer.appendChild(dateDivider);
    }
}

// 날짜 구분선용 날짜 포맷팅 함수
function formatDateDivider(date) {
    return new Intl.DateTimeFormat('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    }).format(date);
}


const createChannelModal = document.getElementById('createChannelModal');
const closeModalButton = createChannelModal.querySelector('.create-channel-close-btn');
const cancelButton = createChannelModal.querySelector('.create-channel-cancel-btn');
const createButton = createChannelModal.querySelector('.create-channel-submit-btn');
const channelNameInput = document.getElementById('channelNameInput');
const channelDescriptionInput = document.getElementById('channelDescriptionInput');

// 채널명 유효성 검사 정규식
const channelNameRegex = /^[a-z0-9-]+$/;

// 모달 열기 함수
function openCreateChannelModal() {
    createChannelModal.style.display = 'flex';
    channelNameInput.value = '';
    channelDescriptionInput.value = '';
    channelNameInput.focus();
    updateCreateButton();
}

// 모달 닫기 함수
function closeCreateChannelModal() {
    createChannelModal.style.display = 'none';
}

// 채널명 유효성 검사 및 생성 버튼 활성화 함수
function updateCreateButton() {
    const channelName = channelNameInput.value.trim();
    const isValid = channelName.length > 0 && channelNameRegex.test(channelName);
    createButton.disabled = !isValid;
}

// 이벤트 리스너 등록
addChannelButton.addEventListener('click', openCreateChannelModal);
closeModalButton.addEventListener('click', closeCreateChannelModal);
cancelButton.addEventListener('click', closeCreateChannelModal);
channelNameInput.addEventListener('input', updateCreateButton);

// 채널 생성 버튼 클릭 이벤트
createButton.addEventListener('click', () => {
    const channelName = channelNameInput.value.trim();
    const channelDescription = channelDescriptionInput.value.trim();

    // TODO: 채널 생성 로직 구현
    console.log('Creating channel:', {
        name: channelName,
        description: channelDescription
    });

    closeCreateChannelModal();
});

// 모달 외부 클릭 시 닫기
createChannelModal.addEventListener('click', (e) => {
    if (e.target === createChannelModal) {
        closeCreateChannelModal();
    }
});

// ESC 키로 모달 닫기
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && createChannelModal.style.display === 'flex') {
        closeCreateChannelModal();
    }
});

// Enter 키로 채널 생성
channelDescriptionInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter' && !createButton.disabled) {
        createButton.click();
    }
});