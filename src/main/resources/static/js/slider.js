// 슬라이더 동작을 위한 JavaScript
const leftBtns = document.querySelectorAll('.left-btn');
const rightBtns = document.querySelectorAll('.right-btn');
const sliderContents = document.querySelectorAll('.slider-content');

leftBtns.forEach((btn, index) => {
    btn.addEventListener('click', () => {
        const sliderWidth = sliderContents[index].clientWidth;
        sliderContents[index].scrollLeft -= sliderWidth;
    });
});

rightBtns.forEach((btn, index) => {
    btn.addEventListener('click', () => {
        const sliderWidth = sliderContents[index].clientWidth;
        sliderContents[index].scrollLeft += sliderWidth;
    });
});


