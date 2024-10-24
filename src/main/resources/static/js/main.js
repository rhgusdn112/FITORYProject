document.addEventListener('DOMContentLoaded', () => {

  if (window.location.pathname === '/') {
    let images = ['main-background1.jpg', 'main-background2.jpg'];
    let container = document.querySelector('.container');
    if (container) {
      let currentIndex = Math.floor(Math.random() * images.length);

      // 이미지 미리 로드하여 빠른 전환 준비
      images.forEach((img) => {
        let image = new Image();
        image.src = `../images/${img}`;
      });

      // 초기 배경 이미지 설정
      container.style.backgroundImage = `url('../images/${images[currentIndex]}')`;

      // 10초마다 배경 이미지 변경
      setInterval(() => {
        currentIndex = (currentIndex + 1) % images.length;
        container.style.backgroundImage = `url('../images/${images[currentIndex]}')`;
      }, 10000);

      // 컨테이너 클릭 시 메인 페이지로 리디렉션
      container.addEventListener('click', () => {
        window.location.href = '/main';
      });
    }
  }

  // 사이드 메뉴 기능을 위한 요소 가져오기
  const sideMenu = document.getElementById('side-menu');
  const menuButton = document.querySelector('.header-menu-icon'); // 클래스명 변경
  const closeButton = document.querySelector('.header-close-icon'); // 클래스명 변경

  // 사이드 메뉴 가시성 토글 함수
  function toggleMenu() {
    if (sideMenu) {
      sideMenu.classList.toggle('open');
      if (sideMenu.classList.contains('open')) {
        sideMenu.style.left = '0';
      } else {
        sideMenu.style.left = '-300px';
      }
    }
  }

  // 메뉴 버튼 클릭 시 사이드 메뉴 열기/닫기
  if (menuButton) {
    menuButton.addEventListener('click', (event) => {
      event.stopPropagation();
      toggleMenu();
    });
  }

  // 닫기 버튼 클릭 시 사이드 메뉴 닫기
  if (closeButton) {
    closeButton.addEventListener('click', (event) => {
      event.stopPropagation();
      toggleMenu();
    });
  }

  // 문서의 다른 부분 클릭 시 사이드 메뉴 닫기
  if (sideMenu) {
    document.addEventListener('click', (event) => {
      if (sideMenu.classList.contains('open')) {
        const clickedInsideMenu = sideMenu.contains(event.target);
        const clickedMenuButton = menuButton && menuButton.contains(event.target);
        if (!clickedInsideMenu && !clickedMenuButton) {
          sideMenu.classList.remove('open');
          sideMenu.style.left = '-300px';
        }
      }
    });
  }

  // 검색 바 상호작용 요소
  const searchInput = document.querySelector('.header-search-input'); // 클래스명 변경
  const clearButton = document.querySelector('.header-clear-button'); // 클래스명 변경
  const searchButton = document.querySelector('.header-search-button'); // 클래스명 변경

  if (searchInput && clearButton && searchButton) {
    // 입력에 따라 클리어 버튼 표시 또는 숨기기
    searchInput.addEventListener('input', () => {
      clearButton.style.display = searchInput.value ? 'inline' : 'none';
    });

    // 클리어 버튼 클릭 시 검색 입력 초기화
    clearButton.addEventListener('click', (event) => {
      event.stopPropagation();
      searchInput.value = '';
      clearButton.style.display = 'none';
    });

    // 검색 버튼 클릭 시 이벤트 전파 방지
    searchButton.addEventListener('click', (event) => {
      event.stopPropagation();
    });

    // 검색 입력 클릭 시 이벤트 전파 방지
    searchInput.addEventListener('click', (event) => {
      event.stopPropagation();
    });
  }
});

// 페이지 맨 위로 부드럽게 스크롤
function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
}

const admin = location.pathname.split("/")[0];
