


export default () => {

    // 모달 관련 요소들 선택
    const modal = document.getElementById('channelModal');
    const channelNameInput = document.getElementById('channelName');
    const createButton = document.getElementById('createButton');
    const cancelButton = document.getElementById('cancelButton');
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
    }

    // 모달 바깥 영역 클릭시 닫기
    modal.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });

    // Cancel 버튼 클릭시 닫기
    cancelButton.addEventListener('click', closeModal);
}