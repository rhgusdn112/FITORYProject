let player;  // 비디오 객체
let popupShown = false;  // 팝업이 표시되었는지 여부를 추적

// 비디오 플레이어 로드 후 호출되는 함수
function initializeVideoPlayer() {
    const videoElement = document.getElementById('video-player');
    const userType = videoElement.dataset.userType || 'guest';  // JavaScript 변수에서 사용자 유형 가져오기

    player = document.createElement('video');
    player.width = 1300;
    player.height = 600;
    player.src = '/video/sample.mp4';  // 실제 비디오 파일 경로
    player.playsInline = true;
    player.controls = true;  // 초기에는 컨트롤러 활성화
    player.oncontextmenu = () => false;  // 우클릭 방지

    videoElement.appendChild(player);

    if (userType === 'guest' || userType === 'unpaidMember') {
        player.addEventListener('play', handleGuestPlay);
    }
}

function handleGuestPlay() {
    // 재생 시작 시 컨트롤러 숨기기
    player.removeAttribute('controls');

    if (!popupShown) {
        setTimeout(() => {
            if (!player.paused) {
                player.pause();  // 일정 시간 후 영상 일시정지
                popupShown = true;  // 팝업이 표시되었음을 기록
                removeVideo();  // 영상을 페이지에서 제거
                document.getElementById('video-popup').style.display = 'block';  // 팝업 표시
            }
        }, 6000);  // 6초 후 일시정지 (실제 사용 시 1분으로 설정 가능)
    }
}

// 팝업에서 '영상 이어보기' 버튼 클릭 시 결제 페이지로 이동
document.getElementById('continue-video').addEventListener('click', function() {
    const videoElement = document.getElementById('video-player');
    const userType = videoElement.dataset.userType || 'guest';
    const classNo = videoElement.dataset.classNo;
    const boardNo = videoElement.dataset.boardNo;

    if (userType === 'unpaidMember') {
        window.location.href = `/payment/${classNo}/${boardNo}`;  // 결제 페이지로 이동
    } else if (userType === 'guest') {
        alert('로그인 후 이용 가능합니다');
        window.location.href = '/login';  // 로그인 페이지로 이동
    }
});

// 팝업에서 '영상 그만보기' 버튼 클릭 시
document.getElementById('stop-video').addEventListener('click', function() {
    document.getElementById('video-popup').style.display = 'none';  // 팝업 닫기
});

// 영상 제거 함수
function removeVideo() {
    if (player) {
        player.pause();
        player.remove();  // 비디오 플레이어 제거
    }
}

// 비디오 초기화 함수 호출
document.addEventListener('DOMContentLoaded', initializeVideoPlayer);

// 우클릭 및 다운로드 방지
window.addEventListener('contextmenu', function(e) {
    e.preventDefault();
});
