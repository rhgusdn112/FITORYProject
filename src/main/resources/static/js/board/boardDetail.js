let player;
let popupShown = false;
let lastKnownPosition = 0;  // 마지막으로 기록된 재생 위치

function initializeVideoPlayer() {
    const videoElement = document.getElementById('video-player');
    const userType = videoElement.dataset.userType || 'guest';

    player = document.createElement('video');
    player.width = 1300;
    player.height = 600;
    player.src = '/video/sample.mp4';  // 비디오 파일 경로
    player.playsInline = true;
    player.controls = true;  // 결제 회원 이상만 컨트롤러 활성화
    player.oncontextmenu = () => false;  // 비디오에서 우클릭 방지
    videoElement.appendChild(player);

    // 게스트 또는 비결제 회원일 경우, 타임라인 탐색 방지 및 재생 제한
    if (userType === 'guest' || userType === 'unpaidMember') {
        player.addEventListener('play', handleGuestPlay);  // 재생 시 6초 후 팝업
        player.addEventListener('seeked', handleSeekPrevent);  // 타임라인 이동 방지
    }

    // 현재 재생 위치 기록 (seeked 이벤트 대비)
    player.addEventListener('timeupdate', () => {
        lastKnownPosition = player.currentTime;
    });
}

// 타임라인 탐색 방지 및 즉시 팝업 표시 함수
function handleSeekPrevent() {
    if (!popupShown) {
        alert("타임라인 이동은 결제 후 이용해주세요.");  // 경고 메시지 표시
        player.pause();  // 비디오 일시정지
        removeVideo();  // 비디오 제거
        popupShown = true;  // 팝업이 이미 표시되었음을 기록
        document.getElementById('video-popup').style.display = 'block';  // 팝업 표시
    }
}

// 게스트 및 비결제 회원이 재생 시 실행되는 함수
function handleGuestPlay() {
    player.removeAttribute('controls');  // 타임라인 없는 컨트롤러

    if (!popupShown) {
        setTimeout(() => {
            if (!player.paused) {
                removeVideo();  // 영상을 페이지에서 제거
                popupShown = true;
                document.getElementById('video-popup').style.display = 'block';  // 팝업 표시
            }
        }, 6000);  // 6초 후 일시정지 및 팝업
    }
}

// 팝업에서 '영상 이어보기' 버튼 클릭 시 결제 페이지로 이동
document.getElementById('continue-video').addEventListener('click', function () {
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

// 팝업에서 '리뷰 읽기' 버튼 클릭 시 팝업 닫기
document.getElementById('stop-video').addEventListener('click', function () {
    document.getElementById('video-popup').style.display = 'none';
});

// 영상 제거 함수
function removeVideo() {
    if (player) {
        player.pause();
        player.remove();  // 비디오 플레이어 제거
    }
}

// 페이지 로드 시 비디오 초기화 함수 호출
document.addEventListener('DOMContentLoaded', initializeVideoPlayer);

// 전체 페이지에서 우클릭 방지
window.addEventListener('contextmenu', function (e) {
    e.preventDefault();
});

document.addEventListener('DOMContentLoaded', function () {
    // 신고 버튼 클릭 시 팝업 열기
    document.querySelectorAll('.report-btn').forEach(button => {
      button.addEventListener('click', function () {
        const reportType = this.getAttribute('data-type');
        const userType = document.getElementById('video-player').dataset.userType || 'guest';
        const boardNo = this.getAttribute('data-board-no') || null;
        const commentNo = this.getAttribute('data-comment-no') || null;
  
        console.log('Board No:', boardNo);   // boardNo 값 확인
        console.log('Comment No:', commentNo); // commentNo 값 확인
  
        if (!boardNo && !commentNo) {
          alert('신고 대상이 잘못되었습니다.');
          return;
        }
  
        // 팝업 열기
        document.getElementById('report-popup').style.display = 'flex';
  
        // 신고자 유형 설정 (회원 또는 강사)
        document.getElementById('reporter-type').value = userType === 'trainer' ? '강사' : '회원';
  
        // 피신고자 유형 설정 (게시글 또는 리뷰)
        document.getElementById('reported-type').value = reportType === 'board' ? '게시글' : '리뷰';
  
        // 피신고자 번호는 모달에는 추가하지 않고 JavaScript 변수로만 관리
        let reportedNo = reportType === 'board' ? boardNo : commentNo;
  
        console.log('Reported No:', reportedNo); // reportedNo 값 확인
  
        // 팝업 제출 시 서버로 전송할 데이터에 해당 번호 추가
        document.getElementById('submit-report').onclick = function () {
          if (reportedNo) {
            submitReport(userType, reportType, reportedNo);
          } else {
            alert('신고 대상 정보가 부족합니다.');
          }
        }
      });
    });
  
    // 팝업 닫기
    document.getElementById('close-popup').addEventListener('click', function () {
      document.getElementById('report-popup').style.display = 'none';
  
      // 입력 필드 초기화
      document.getElementById('report-title').value = '';
      document.getElementById('report-message').value = '';
    });
  
    // 신고 제출
    function submitReport(userType, reportedType, reportedNo) {
      const reportTitle = document.getElementById('report-title').value;
      const reportMessage = document.getElementById('report-message').value;
  
      if (!reportTitle || !reportMessage) {
        alert('신고 제목과 내용을 모두 입력해주세요.');
        return;
      }
  
      // 서버로 데이터 전송
      fetch('/admin/report', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          reporterType: userType,
          reportedType: reportedType,
          reportedNo: reportedNo,
          reportTitle: reportTitle,
          reportMessage: reportMessage
        })
      }).then(response => {
        if (response.ok) {
          alert('신고가 접수되었습니다.');
          document.getElementById('report-popup').style.display = 'none';
  
          // 입력 필드 초기화
          document.getElementById('report-title').value = '';
          document.getElementById('report-message').value = '';
        } else {
          throw new Error('신고 접수에 실패했습니다.');
        }
      }).catch(error => {
        console.error('Error submitting report:', error);
        alert('신고 제출 중 오류가 발생했습니다.');
      });
    }
  });
  


document.addEventListener('DOMContentLoaded', function () {
    // 1:1 문의 버튼 이벤트 핸들러
    document.querySelectorAll('.inquiry-btn').forEach(button => {
      button.addEventListener('click', function () {
        const boardNo = button.getAttribute('data-board-no');
        const userType = button.querySelector('span').textContent;
  
        // 1:1 문의 팝업에 데이터 설정
        document.getElementById('inquiry-user-type').value = userType;
        document.getElementById('inquiry-target').value = `게시글 번호 ${boardNo}`;
        
        // 팝업 열기
        document.getElementById('inquiry-popup').style.display = 'flex';
      });
    });
  
    // 팝업 닫기 버튼
    document.getElementById('close-inquiry-popup').addEventListener('click', function () {
      document.getElementById('inquiry-popup').style.display = 'none';
      // 입력 필드 초기화
      document.getElementById('inquiry-title').value = '';
      document.getElementById('inquiry-message').value = '';
      document.getElementById('inquiry-target').value = '';
    });
  
    // 문의 제출 버튼
    document.getElementById('submit-inquiry').addEventListener('click', function () {
      const inquiryTitle = document.getElementById('inquiry-title').value;
      const inquiryMessage = document.getElementById('inquiry-message').value;
  
      if (inquiryTitle && inquiryMessage) {
        // 서버로 전송할 데이터 구성
        const requestData = {
          title: inquiryTitle,
          message: inquiryMessage,
          target: document.getElementById('inquiry-target').value
        };
  
        console.log('1:1 문의 제출:', requestData);
  
        // 여기에 fetch 등을 사용해서 서버로 문의 요청 전송 가능
        alert('문의가 제출되었습니다.');
        document.getElementById('inquiry-popup').style.display = 'none';
        // 입력 필드 초기화
        document.getElementById('inquiry-title').value = '';
        document.getElementById('inquiry-message').value = '';
        document.getElementById('inquiry-target').value = '';
      } else {
        alert('모든 항목을 입력해주세요.');
      }
    });
  });
  


