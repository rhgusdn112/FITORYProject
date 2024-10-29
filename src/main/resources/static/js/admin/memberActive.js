// 화면 구성시 비동기로 실행되는 기능
document.addEventListener("DOMContentLoaded",()=>{
  selectOrderList();
  selectCommentList();
  selectQueryList();
  selectReportList();
})

// 회원 관리 페이지

// 회원번호 주소에서 받아오기
const memberNo = location.href.split("=")[1];
// console.log(memberNo);

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
      td2.innerText = order.title; // 제목

      const td3 = document.createElement("td");
      td3.innerText = order.orderClassAmount; // 가격

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
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
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
      const commentDirect = document.createElement("a")

      commentDirect.innerText = comment.reviewContent; // 내용

      commentDirect.href = "/board/"+ comment.classNo 
                            + "/" + comment.boardNo + "?commentNo=" + comment.commentNo;

      td2.append(commentDirect);
      
      const td3 = document.createElement("td");
      td3.innerText = comment.reviewWriteDate; // 작성일자

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
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    console.log(list);
    queryList.innerHTML = "";

    list.forEach(query => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = query.queryNo; // 번호

      const td2 = document.createElement("td");
      const queryType = document.createElement("a");

      queryType.innerText = query.queryType; // 문의 종류
      queryType.href = "#";
      td2.append(queryType);

      const td3 = document.createElement("td");
      let status = "";
        if(query.status === 1){
          status = "진행 중";
        } else{
          status = "처리완료";
        }
      td3.innerText = status; // 진행 상태

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

      const reportDirect = document.createElement("a");
      reportDirect.innerText = report.reportType; // 신고 종류
      reportDirect.href = "/admin/report?reportNo=" + report.reportNo;

      td2.append(reportDirect);

      
      const td3 = document.createElement("td");
      td2.innerText = report.status; // 진행상태
      
      tr.append(td1, td2, td3);

      reportList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

