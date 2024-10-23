let player;  // YouTube Player 객체
let isLoggedIn = /*[[${session.loginMember != null}]]*/ false; // Thymeleaf로 로그인 여부 전달

// YouTube IFrame API 로드 후 호출되는 함수
function onYouTubeIframeAPIReady() {
    player = new YT.Player('youtube-player', {
        height: '315',
        width: '560',
        videoId: 'dQw4w9WgXcQ',  // 실제 YouTube 영상 ID 입력
        playerVars: {
            'controls': isLoggedIn ? 1 : 0,  // 로그인이 되어 있지 않으면 컨트롤러 비활성화
            'disablekb': 1  // 키보드 컨트롤 비활성화 (로그인 전)
        },
        events: {
            'onStateChange': onPlayerStateChange  // 이벤트 연결
        }
    });
}

// 영상 상태 변경 시 호출되는 함수
function onPlayerStateChange(event) {
    if (!isLoggedIn && event.data == YT.PlayerState.PLAYING) {
        // 로그인이 안 되어 있고 영상이 재생 중일 때
        setTimeout(function() {
            player.pauseVideo();  // 1분 후 영상 일시정지
            document.getElementById('video-popup').style.display = 'block';  // 팝업 표시
        }, 6000);  // 1분 후 실행 (확인중이라 임시로 6초 설정)
    }
}

// 팝업에서 '영상 이어보기' 버튼 클릭 시
document.getElementById('continue-video').addEventListener('click', function() {
    window.location.href = '/payments';  // 결제 페이지로 이동
});

// 팝업에서 '영상 그만보기' 버튼 클릭 시
document.getElementById('stop-video').addEventListener('click', function() {
    document.getElementById('video-popup').style.display = 'none';  // 팝업 닫기
});
