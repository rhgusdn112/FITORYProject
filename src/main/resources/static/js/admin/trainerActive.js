// 화면 구성시 비동기로 실행되는 기능
document.addEventListener("DOMContentLoaded",()=>{
  selectQualificationList();
  selectboardList();
  selectQueryList();
  selectReportList();
})

// 강사 관리 페이지

// 강사번호 주소에서 받아오기
const trainerNo = location.href.split("=")[1];
// console.log(memberNo);

//강사 자격정보 조회
const qualificationList = document.querySelector("#qualificationList");

const selectQualificationList = () => {

  fetch("/admin/qualificationList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : trainerNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    qualificationList.innerHTML = "";

    list.forEach(trainer => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = trainer.qualificationNo; // 자격 번호

      const td2 = document.createElement("td");
      td2.innerText = trainer.qualification; // 자격 정보

      const td3 = document.createElement("td");
      td3.innerText = trainer.qualificationDate; // 취득일자

      tr.append(td1, td2, td3);

      qualificationList.append(tr);
    })
  })
  .catch(err => console.error(err));
}


// 게시물 작성내역 조회
const boardList = document.querySelector("#boardList");

const selectboardList = () => {

  fetch("/admin/boardList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : trainerNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    boardList.innerHTML = "";

    list.forEach(board => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = board.boardNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = board.title; // 제목
      
      const td3 = document.createElement("td");
      td2.innerText = board.uploadDate; // 작성일자

      tr.append(td1, td2, td3);

      boardList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

// 문의 내역 조회
const queryList = document.querySelector("#queryList");

const selectQueryList = () => {

  fetch("/admin/trainerQueryList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : trainerNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    queryList.innerHTML = "";

    list.forEach(query => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = query.queryNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = query.queryType; // 문의 종류

      const td3 = document.createElement("td");

      td3.innerText = query.status; // 진행 상태
      tr.append(td1, td2, td3);

      queryList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

// 신고 내역 조회
const reportList = document.querySelector("#queryList");

const selectReportList = () => {

  fetch("/admin/trainerReportList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : trainerNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    reportList.innerHTML = "";

    list.forEach(report => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = report.reportNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = report.reportTitle; // 내용
      
      const td3 = document.createElement("td");
      td2.innerText = report.status; // 진행상태
      
      tr.append(td1, td2, td3);

      reportList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

