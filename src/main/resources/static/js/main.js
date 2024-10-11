document.addEventListener('DOMContentLoaded', () => {
  let images = ['main-background1.jpg', 'main-background2.jpg'];
  let container = document.querySelector('.container');
  let currentIndex = Math.floor(Math.random() * images.length);

  // 미리 배경 이미지 로드
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

  // 검색 바 상호작용
  const searchInput = document.querySelector('.search-input');
  const clearButton = document.querySelector('.clear-button');
  const searchButton = document.querySelector('.search-button');

  // 검색 입력 시 삭제 버튼 표시
  searchInput.addEventListener('input', () => {
    clearButton.style.display = searchInput.value ? 'inline' : 'none';
  });

  // 삭제 버튼 클릭 시 입력창 비우기
  clearButton.addEventListener('click', (event) => {
    event.stopPropagation();
    searchInput.value = '';
    clearButton.style.display = 'none';
  });

  // 돋보기 버튼 클릭 시 페이지 리다이렉트 방지
  searchButton.addEventListener('click', (event) => {
    event.stopPropagation();
  });

  // 검색 바 클릭 시 페이지 리다이렉트 방지
  searchInput.addEventListener('click', (event) => {
    event.stopPropagation();
  });

  // 배경과 로고 클릭 시 메인 페이지로 리다이렉트
  container.addEventListener('click', () => {
    window.location.href = '/main';
  });
});

// 사이드 메뉴 열고 닫기 기능을 위한 JavaScript 함수
function toggleMenu() {
  const menu = document.getElementById('side-menu');
  menu.classList.toggle('open'); // 'open' 클래스가 있으면 제거, 없으면 추가
}

// 사이드 메뉴 열고 닫기 기능을 위한 JavaScript 함수
function toggleMenu() {
  const menu = document.getElementById('side-menu');
  menu.classList.toggle('open'); // 'open' 클래스가 있으면 제거, 없으면 추가
}

// 맨 위로 이동하는 함수
function scrollToTop() {
  window.scrollTo({
      top: 0,
      behavior: 'smooth' // 스크롤을 부드럽게 올리기
  });
}
