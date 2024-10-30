document.addEventListener('DOMContentLoaded', function () {
  const sliders = document.querySelectorAll('.slider-container');

  sliders.forEach(slider => {
    const list = slider.querySelector('.class-list');
    const items = slider.querySelectorAll('.class-item');
    const prevBtn = slider.querySelector('.left-btn');
    const nextBtn = slider.querySelector('.right-btn');

    let currentIndex = 0;
    const itemsPerView = 4;
    const totalSlides = Math.ceil(items.length / itemsPerView);

    // 초기 버튼 상태 설정
    updateButtonStates();

    // 이전 버튼 클릭
    prevBtn.addEventListener('click', () => {
      if (currentIndex > 0) {
        currentIndex--;
        updateSliderPosition();
        updateButtonStates();
      }
    });

    // 다음 버튼 클릭
    nextBtn.addEventListener('click', () => {
      if (currentIndex < totalSlides - 1) {
        currentIndex++;
        updateSliderPosition();
        updateButtonStates();
      }
    });

    // 슬라이더 위치 업데이트
    function updateSliderPosition() {
      const translateX = -currentIndex * (100);
      list.style.transform = `translateX(${translateX}%)`;
    }

    // 버튼 상태 업데이트
    function updateButtonStates() {
      prevBtn.disabled = currentIndex === 0;
      nextBtn.disabled = currentIndex === totalSlides - 1;
    }
  });

  // 페이지네이션 클릭 이벤트 추가
  paginationAddEvent()
});




/**
 * 비동기로 실시간 클래스 조회(페이지, 정렬)
 * @param {*} page : 조회할 패이지
 * @param {*} sort : 정렬 기준(latest, rating, views)
 */
const selectClassListFn = (page, sort) => {
  const url = `${location.pathname}/list?cp=${page}&sort=${sort}`;

  fetch(url)
    .then(resp => {
      if(resp.ok) return resp.json()
      throw new Error("조회 실패")
    })
    .then(data => {
      console.log(data); // 서버로부터 전달 받음 map

      // 구조 분해 할당 : 객체의 속성 값을 하나 씩 추출할 떄 사용
      //                 {} 내부에 키값과 같은 변수명을 작성하면 하나씩 꺼내서 담아줌 
      const {classList, pagination} = data; 

      // 실시간 클래스를 감싸는 요소
      const classContainer = document.querySelector('#classContainer');
      classContainer.innerHTML = '';


      // 실시간 클래스 목록 출력
      classList.forEach(board => {

        // 감싸는 a 태그
        const a = document.createElement('a');
        a.classList.add('class-item');
        a.href = `/board/${board.classNo}/${board.boardNo}`;

        // 썸네일
        const img = document.createElement('img');
        img.src = board.thumbnail;


        // 강사명 | 제목
        const p = document.createElement('p');
        p.textContent = `${board.trainerNickname} | ${board.title}`;

        a.append(img, p);
        classContainer.append(a);
      });

    
      // 페이지네이션 출력
      const pg = document.querySelector('.pagination');
      pg.innerHTML = '';
      
      // pagication 구조 분해
      const {startPage, endPage, currentPage, prevPage, nextPage, maxPage} = pagination;

      // 버튼 생성 + 화면 추가 함수
      const createPageBtn = (page, text) => {
        const a = document.createElement('a');
        a.textContent = text;
        a.dataset.page = page;

        if(!isNaN(Number(text)) &&  page == currentPage) a.classList.add('current');
        pg.append(a);
      }

      createPageBtn(1, '처음');
      createPageBtn(prevPage, '이전');

      for(let i = startPage; i <= endPage; i++) {
        createPageBtn(i, i);
      }

      createPageBtn(nextPage, '다음');
      createPageBtn(maxPage, '마지막');

      // 페이지네이션 클릭 이벤트 추가
      paginationAddEvent();

    })
    .catch(err => console.log(err));
}


/**
 * 정렬 기준 변경 이벤트
 */
const sortSelect = document.querySelector('#sortSelect');
sortSelect.addEventListener('change', () => {
  selectClassListFn(1, sortSelect.value);
});


/**
 * 페이지네이션 클릭 이벤트 추가
 */
const paginationAddEvent = () => {
  const pagination = document.querySelectorAll('.pagination a');
  pagination.forEach(a => {
    a.addEventListener('click', (e) => {

      if(a.classList.contains('current')) return;
      
      const cp = e.target.dataset.page;
      selectClassListFn(cp, sortSelect.value);
    });
  });
}
