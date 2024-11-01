const classList = document.querySelector("#classList");

const selectClassList = (cp) => {
  fetch("/trainer/classList?cp=" + cp )
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 실패");
  })
  .then(map => {

    console.log(map);
    const list = map.classList;
    const pagination = map.pagination;

    classList.innerHTML = "";

    for(let board of list){
      const tr = document.createElement("tr");

      const th1 = document.createElement("th");
      const a   = document.createElement("a");
      a.href = `/board/${board.classNo}/${board.boardNo}`;

      const img = document.createElement("img");
      img.src = board.thumbnail;

      img.style.width = '150px'; // 원하는 너비
      img.style.height = '150px'; // 원하는 높이
      img.style.objectFit = 'cover'; // 이미지 비율을 유지하면서 잘라내기

      a.append(img);
      th1.append(a);

      const th2 = document.createElement("th");
      const div1 = document.createElement("div");
      div1.innerHTML= "Title : " + board.title;

      const div2 = document.createElement("div");
      div2.innerHTML = "설명 : " + board.detail;

      const div3 = document.createElement("div");
      div3.innerHTML = "강사 : " + board.trainerNickname;

      th2.append(div1, div2, div3);

      const th3 = document.createElement("th");
      const input = document.createElement("input");
      input.type = "checkbox";
      input.name = "checkbox";

      th3.append(input);

      tr.append(th1, th2, th3);

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

      
      const boardWriteBtn = document.querySelector("#boardWriteBtn");
  })
  .catch(err => console.error(err));
}

const selectAllBtn = document.querySelector("#selectAllBtn");
selectAllBtn?.addEventListener("click", () => {
  const checkbox = document.querySelector("#checkbox");
  checkbox.checked;
  
})

const paginationAddEvent = () => {
  const pagination = document.querySelectorAll('.pagination a');
  pagination.forEach(a => {
    a.addEventListener('click', (e) => {

      if(a.classList.contains('current')) return;
      
      const cp = e.target.dataset.page;
      selectClassList(cp);
    });
  });
}


document.addEventListener("DOMContentLoaded", ()=>{
  selectClassList(1);
})