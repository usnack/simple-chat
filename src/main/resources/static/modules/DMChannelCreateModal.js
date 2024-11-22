
export default () => {

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

}