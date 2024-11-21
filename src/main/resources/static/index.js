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

// 모달 관련 요소들 선택
const modal = document.getElementById('channelModal');
const channelNameInput = document.getElementById('channelName');
const createButton = document.getElementById('createButton');
const cancelButton = document.getElementById('cancelButton');
const typeOptions = document.querySelectorAll('.channel-type-option');
const inputPrefix = document.querySelector('.form-input-prefix');

// + 버튼에 클릭 이벤트 추가
document.querySelectorAll('.add-group-channel').forEach(button => {
    button.addEventListener('click', (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
        modal.style.display = 'block'; // 모달 보이기
        channelNameInput.focus(); // 채널명 입력란에 포커스
    });
});

// 모달 닫기 함수
function closeModal() {
    modal.style.display = 'none';
    channelNameInput.value = '';
    createButton.disabled = true;
    typeOptions[0].classList.add('selected');
    typeOptions[1].classList.remove('selected');
}

// 모달 바깥 영역 클릭시 닫기
modal.addEventListener('click', (e) => {
    if (e.target === modal) closeModal();
});

// Cancel 버튼 클릭시 닫기
cancelButton.addEventListener('click', closeModal);


// DM 모달 관련 요소들
const dmModal = document.getElementById('dmModal');
const dmCancelButton = document.getElementById('dmCancelButton');
const dmCreateButton = document.getElementById('dmCreateButton');
const dmUserSearch = document.getElementById('dmUserSearch');
const dmSelectedUsers = document.getElementById('selectedUsers');

// DM 추가 버튼 클릭 이벤트
document.querySelectorAll('.add-direct-channel').forEach(button => {
    const parentCategory = button.closest('.channel-category');
    if (parentCategory && parentCategory.id === 'directMessagesCategory') {
        button.addEventListener('click', (e) => {
            e.stopPropagation();
            dmModal.style.display = 'block';
            dmUserSearch.focus();
        });
    }
});

// DM 모달 닫기 함수
function closeDmModal() {
    dmModal.style.display = 'none';
    dmUserSearch.value = '';
    dmSelectedUsers.innerHTML = '';
}

// DM 모달 바깥 영역 클릭시 닫기
dmModal.addEventListener('click', (e) => {
    if (e.target === dmModal) closeDmModal();
});

// DM 취소 버튼 클릭시 닫기
dmCancelButton.addEventListener('click', closeDmModal);

// DM 사용자 선택 토글
document.querySelectorAll('.dm-user-item').forEach(item => {
    item.addEventListener('click', () => {
        item.classList.toggle('selected');
        updateDmSelectedUsers();
        updateDmCreateButton();
    });
});

// 선택된 사용자 업데이트
function updateDmSelectedUsers() {
    const selected = document.querySelectorAll('.dm-user-item.selected');
    dmSelectedUsers.innerHTML = '';

    selected.forEach(user => {
        const userName = user.querySelector('.dm-user-name').textContent;
        const tag = document.createElement('div');
        tag.className = 'dm-selected-tag';
        tag.innerHTML = `
            ${userName}
            <div class="dm-remove-user">
                <i class="fas fa-times"></i>
            </div>
        `;

        tag.querySelector('.dm-remove-user').addEventListener('click', () => {
            user.classList.remove('selected');
            updateDmSelectedUsers();
            updateDmCreateButton();
        });

        dmSelectedUsers.appendChild(tag);
    });
}

// DM 생성 버튼 상태 업데이트
function updateDmCreateButton() {
    const selected = document.querySelectorAll('.dm-user-item.selected');
    dmCreateButton.disabled = selected.length === 0;
}

// 프로필 모달 관련 요소들
const profileModal = document.getElementById('profileModal');
const userAvatars = document.querySelectorAll('.user-avatar, .member-avatar, .message-avatar'); // 프로필 이미지들

// 프로필 이미지 클릭 시 모달 열기
userAvatars.forEach(avatar => {
    avatar.addEventListener('click', (e) => {
        e.stopPropagation();
        profileModal.style.display = 'block';
    });
});

// 프로필 모달 닫기 함수
function closeProfileModal() {
    profileModal.style.display = 'none';
}

// 모달 바깥 영역 클릭시 닫기
profileModal.addEventListener('click', (e) => {
    if (e.target === profileModal) {
        closeProfileModal();
    }
});

// ESC 키로 모달 닫기
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && profileModal.style.display === 'block') {
        closeProfileModal();
    }
});