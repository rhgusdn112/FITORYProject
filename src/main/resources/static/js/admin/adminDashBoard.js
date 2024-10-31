// tbody 내 데이터 작성할 곳 변수명 선언
const classList = document.querySelector("#classList");
const boardList = document.querySelector("#boardList");

// classList는 classNo = 2인 게시물
const selectClassList = (cp) => {
  fetch("/admin/classList?cp="+cp)
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 실패");
  })
  .then(map => {
    // console.log(map);

    const list = map.classList;
    const pagination = map.pagination

    classList.innerHTML = "";
    
    for(let board of list){
   
      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = board.boardNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = board.title;

      const td3 = document.createElement("td");
      td3.innerText = board.payment; 

      const td4 = document.createElement("td");
      td4.innerText = board.grade; // 평점 X -> 총 매출

      tr.append(td1, td2, td3, td4);

      classList.append(tr);

    }
      
    
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

      for(let i = startPage; i < endPage; i++) {
        createPageBtn(i, i);
      }

      createPageBtn(nextPage, '다음');
      createPageBtn(maxPage, '마지막');

      paginationAddEvent();
  })
  .catch(err => console.error(err));
}


// boardList는 classNo = 3인 게시물
const selectBoardList = (cp) => {
  fetch("/admin/boardList?cp="+cp)
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 실패");
  })
  .then(map => {
    console.log(map);

    const list = map.boardList;
    const pagination = map.pagination

    boardList.innerHTML = "";
    
    for(let board of list){
   
   
      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = board.boardNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = board.title;

      const td3 = document.createElement("td");
      td3.innerText = board.payment; 

      const td4 = document.createElement("td");
      td4.innerText = board.grade; // 평점 X -> 총 매출

      tr.append(td1, td2, td3, td4);

      boardList.append(tr);
    }

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

   for(let i = startPage; i < endPage; i++) {
     createPageBtn(i, i);
   }

   createPageBtn(nextPage, '다음');
   createPageBtn(maxPage, '마지막');

   paginationAddEvent();

  })
  .catch(err => console.error(err));
}

const paginationAddEvent = () => {
  const pagination = document.querySelectorAll('.pagination a');
  pagination.forEach(a => {
    a.addEventListener('click', (e) => {

      if(a.classList.contains('current')) return;
      
      const cp = e.target.dataset.page;
      selectClassList(cp);
      selectBoardList(cp);
    });
  });
}


document.addEventListener("DOMContentLoaded", ()=>{
  selectClassList(1);
  selectBoardList(1);
})