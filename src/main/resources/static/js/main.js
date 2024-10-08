let currentBackgroundIndex = Math.floor(Math.random() * 2) + 1;
const container = document.querySelector('.container');

function changeBackground() {
  currentBackgroundIndex = currentBackgroundIndex === 1 ? 2 : 1;
  container.style.backgroundImage = `url('../images/main-background${currentBackgroundIndex}.jpg')`;
}

// 초기 배경 설정
container.style.backgroundImage = `url('../images/main-background${currentBackgroundIndex}.jpg')`;

// 15초마다 배경 변경
setInterval(changeBackground, 10000);

// 배경 클릭 시 메인 페이지로 이동
container.addEventListener('click', () => {
  window.location.href = '/main';
});

// 검색 입력창 초기화 버튼 보이기/숨기기
const searchInput = document.querySelector('.search-input');
const clearButton = document.querySelector('.clear-button');

searchInput.addEventListener('input', () => {
  clearButton.style.display = searchInput.value ? 'block' : 'none';
});

clearButton.addEventListener('click', () => {
  searchInput.value = '';
  clearButton.style.display = 'none';
});