


export default () => {

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
}
