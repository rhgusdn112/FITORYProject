let player;  // YouTube Player 객체
let popupShown = false;  // 팝업이 표시되었는지 여부를 추적

// IFrame API 준비 상태를 체크하는 함수
function onYouTubeIframeAPIReady() {
    const videoElement = document.getElementById('youtube-player');
    const videoId = videoElement.getAttribute('data-detail');  // HTML에서 YouTube 영상 ID 가져오기

    // YouTube Player 생성
    player = new YT.Player('youtube-player', {
        height: '315',
        width: '560',
        videoId: videoId,  // 실제 YouTube 영상 ID
        playerVars: {
            'controls': isLoggedIn ? 1 : 0,  // 로그인 여부에 따라 컨트롤러 활성화
            'disablekb': isLoggedIn ? 0 : 1,  // 비로그인 상태에서는 키보드 컨트롤 제한
            'rel': 0,  // 영상 종료 후 관련 영상 표시 비활성화
            'showinfo': 0  // 정보 표시 비활성화
        },
        events: {
            'onStateChange': onPlayerStateChange  // 영상 상태 변경 시 호출될 함수
        }
    });
}

// 영상 상태 변경 시 호출되는 함수
function onPlayerStateChange(event) {
    if (!isLoggedIn && event.data == YT.PlayerState.PLAYING && !popupShown) {
        // 비로그인 상태에서 일정 시간 후 영상 일시정지 및 팝업 표시
        setTimeout(function() {
            player.pauseVideo();  // 일정 시간 후 영상 일시정지
            popupShown = true;  // 팝업이 표시되었음을 기록
            document.getElementById('video-popup').style.display = 'block';  // 팝업 표시
        }, 6000);  // 6초 후 일시정지 (실제 사용 시 1분으로 설정 가능)
    }
}

// 팝업에서 '영상 이어보기' 버튼 클릭 시 결제 페이지로 이동
document.getElementById('continue-video').addEventListener('click', function() {
    window.location.href = '/payment';  // 결제 페이지로 이동
});

// 팝업에서 '영상 그만보기' 버튼 클릭 시
document.getElementById('stop-video').addEventListener('click', function() {
    document.getElementById('video-popup').style.display = 'none';  // 팝업 닫기
    removeVideo();  // 영상을 페이지에서 제거
});

// 영상 제거 함수 (YouTube IFrame API 사용)
function removeVideo() {
    if (player) {
        player.destroy();  // YouTube IFrame 플레이어 제거
    }
}
