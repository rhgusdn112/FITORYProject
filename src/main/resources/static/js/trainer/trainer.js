
// ----------------------------------------------------------------------------------------------
/* 강사 정보 상세조회 */
const url = "/trainer/trainerDetail/{trainerNo:[0-9]+}";
const trainerDetailNo = /^\/trainer\/trainerDetail\/([0-9]+)$/;

// 정규식을 사용하여 URL에서 trainerNo 부분 추출
const match = url.match(trainerDetailNo);

// 'watch' 버튼 클릭 시 처리
const watchBtn = document.getElementById("watch");
if (watchBtn) {
  watchBtn.addEventListener("click", () => {
    const trainerNoElement = document.getElementById('trainerNickname');
    const trainerNo = trainerNoElement ? trainerNoElement.getAttribute('data-trainerNo') : null;
    if (match) {
      const trainerNoFromUrl = match[1];
      console.log("trainerNo (from URL):", trainerNoFromUrl);
    } else if (trainerNo) {
      location.href = `/trainer/trainerVideoDetail?cp=${trainerNo}`;
    } else {
      console.error("Error");
    }
  });
}

//--------------------------------------------------------------------------------------------------
/* 선택한 강사 영상 리스트 
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
      const {trainerClassList, pagination} = data; 

      // 실시간 클래스를 감싸는 요소
      const classContainer = document.querySelector('#classContainer');
      classContainer.innerHTML = '';


      // 실시간 클래스 목록 출력
      classList.forEach(board => {

        // 감싸는 a 태그
        const a = document.createElement('a');
        a.trainerClassList.add('class-item');
        a.href = `/board/${board.trainerNo}`;

        // 썸네일
        const img = document.createElement('img');
        img.src = board.thumbnail;


        // 제목 | 카테고리
        const p = document.createElement('p');
        p.textContent = `${board.title} | ${board.boardTypeName}`;

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

/* 정렬 기준 변경 이벤트 */
const sortSelect = document.querySelector('#sortSelect');
sortSelect.addEventListener('change', () => {
  selectClassListFn(1, sortSelect.value);
});


/* 페이지네이션 클릭 이벤트 추가 */
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
