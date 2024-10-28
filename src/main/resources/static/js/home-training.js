document.querySelectorAll('.slider-btn').forEach(button => {
  const section = button.dataset.section;
  const classList = button.closest('.slider-container').querySelector('.class-list');
  let scrollAmount = 0;
  const scrollStep = 220; // 이미지 크기만큼 스크롤

  button.addEventListener('click', () => {
      if (button.classList.contains('left-btn')) {
          scrollAmount -= scrollStep;
      } else {
          scrollAmount += scrollStep;
      }
      classList.scrollTo({
          top: 0,
          left: scrollAmount,
          behavior: 'smooth'
      });
  });
});
