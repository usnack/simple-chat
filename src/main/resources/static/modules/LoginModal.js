export default () => {
    // 로그인 모달 관련 요소들
    const loginModal = document.getElementById('loginModal');
    const loginForm = document.querySelector('.login-form');

// 로그인 모달 열기 함수
    function openLoginModal() {
        loginModal.style.display = 'block';
    }

// 로그인 모달 닫기 함수
    function closeLoginModal() {
        loginModal.style.display = 'none';
        // 폼 리셋
        loginForm.reset();
    }

// 모달 바깥 영역 클릭시 닫기
    loginModal.addEventListener('click', (e) => {
        if (e.target === loginModal) {
            closeLoginModal();
        }
    });

// 폼 제출 처리
    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        // 여기에 로그인 로직 추가
        console.log('Login submitted');
    });

// ESC 키로 모달 닫기
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && loginModal.style.display === 'block') {
            closeLoginModal();
        }
    });

    openLoginModal();
}