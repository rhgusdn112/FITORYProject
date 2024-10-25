// 화면 구성시 비동기로 실행되는 기능
document.addEventListener("DOMContentLoaded",()=>{
  selectOrderList();
  selectCommentList();
  selectQueryList();
  selectReportList();
})


// 주문내역 조회
const orderList = document.querySelector("#orderList");

const selectOrderList = () => {

  fetch("/admin/oderList")
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    oderList.innerHTML = "";

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

      oderList.append(tr);
    })
  })
  .catch(err => console.error(err));
}


// 댓글 작성내역 조회
const commentList = document.querySelector("#commentList");

const selectCommentList = () => {

  fetch("/admin/commentList")
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
      td2.innerText = comment.commentDetatl; // 내용

      tr.append(td1, td2);

      commentList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

// 문의 내역 조회
const queryList = document.querySelector("#queryList");

const selectQueryList = () => {

  fetch("/admin/queryList")
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
      td2.innerText = query.queryTitle; // 내용

      tr.append(td1, td2);

      queryList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

// 신고 내역 조회
const reportList = document.querySelector("#queryList");

const selectReportList = () => {

  fetch("/admin/reportList")
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

      tr.append(td1, td2);

      reportList.append(tr);
    })
  })
  .catch(err => console.error(err));
}