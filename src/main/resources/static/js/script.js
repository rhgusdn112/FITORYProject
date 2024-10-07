const images = [
  '/static/images/main-background.jpg',
  '/static/images/alternative-background.jpg'
];

let currentIndex = 0;

function changeBackground() {
  document.querySelector('.container').style.backgroundImage = `url(${images[currentIndex]})`;
  currentIndex = (currentIndex + 1) % images.length;
}

window.onload = function() {
  changeBackground();
  setInterval(changeBackground, 5000);
};

// 검색창 clear 버튼 기능
document.addEventListener("DOMContentLoaded", function() {
  const searchInput = document.querySelector(".search-input");
  const clearButton = document.querySelector(".clear-button");

  searchInput.addEventListener("input", function() {
      if (searchInput.value.length > 0) {
          clearButton.style.display = "inline";
      } else {
          clearButton.style.display = "none";
      }
  });

  clearButton.addEventListener("click", function() {
      searchInput.value = "";
      clearButton.style.display = "none";
  });
});
