// 화면 구성시 비동기로 실행되는 기능
document.addEventListener("DOMContentLoaded",()=>{
  selectOrderList();
  selectCommentList();
  selectQueryList();
  selectReportList();
})

// 주소에서 회원번호 강사 번호 받아오기
const category = location.href.split("=")[0].split("?")[1];
console.log(category);

let memberNo = "";
let trainerNo = "";

if(category == "memberNo"){

  memberNo = location.href.split("=")[1];

}else if(category == "trainerNo"){

  trainerNo = location.href.split("=")[1];

}


// 주문내역 조회
const orderList = document.querySelector("#orderList");

const selectOrderList = () => {

  fetch("/admin/orderList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : memberNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    orderList.innerHTML = "";

    list.forEach(order => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = order.orderNo; // 주문번호

      const td2 = document.createElement("td");
      td2.innerText = order.orderTitle; // 제목

      const td3 = document.createElement("td");
      td3.innerText = order.orderPayment; // 가격

      const td4 = document.createElement("td");
      td4.innerText = order.paymentDate;   // 결제일

      tr.append(td1, td2, td3, td4);

      orderList.append(tr);
    })
  })
  .catch(err => console.error(err));
}


// 댓글 작성내역 조회
const commentList = document.querySelector("#commentList");

const selectCommentList = () => {

  fetch("/admin/commentList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : memberNo
  })
  .then(list => {

    // console.log(list);
    commentList.innerHTML = "";

    list.forEach(comment => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = comment.commentNo; // 번호

      const td2 = document.createElement("td");
      td2.innerText = comment.reviewContent; // 내용
      
      const td3 = document.createElement("td");
      td2.innerText = comment.reviewWriteDate; // 작성일자

      tr.append(td1, td2, td3);

      commentList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

// 문의 내역 조회
const queryList = document.querySelector("#queryList");

const selectQueryList = () => {

  fetch("/admin/queryList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : memberNo
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

  fetch("/admin/reportList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : memberNo
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


// ----------------------------------------------------------------------------
/* 강사 관련 비동기 요청 */


// 문의 내역 조회
const queryList = document.querySelector("#queryList");

const selectQueryList = () => {

  fetch("/admin/queryList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : memberNo
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

  fetch("/admin/reportList",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : trainerNo
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