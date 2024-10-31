document.addEventListener("DOMContentLoaded", () => {
  const inquiryList = document.querySelector("#inquiryList");
  const pagination = document.querySelector("#pagination ul");

  // 비동기적으로 1:1 문의 목록 가져오기
  const fetchInquiryList = (page = 1) => {
    fetch(`/api/inquiryList?page=${page}`)
      .then(response => {
        if (response.ok) return response.json();
        throw new Error("조회 오류");
      })
      .then(data => {
        const { list, totalPages } = data;

        // 문의 목록 테이블 업데이트
        inquiryList.innerHTML = "";
        list.forEach((inquiry, index) => {
          const tr = document.createElement("tr");

          const td1 = document.createElement("td");
          td1.innerText = inquiry.content;

          const td2 = document.createElement("td");
          td2.innerText = inquiry.nickname;

          const td3 = document.createElement("td");
          td3.innerText = inquiry.createdDate;

          const td4 = document.createElement("td");
          const replyBtn = document.createElement("button");
          replyBtn.innerText = "문의 답변하기";
          replyBtn.addEventListener("click", () => {
            alert(`문의 답변하기: ${inquiry.id}`);
          });
          td4.append(replyBtn);

          tr.append(td1, td2, td3, td4);
          inquiryList.append(tr);
        });

        // 페이지네이션 업데이트
        pagination.innerHTML = "";
        for (let i = 1; i <= totalPages; i++) {
          const li = document.createElement("li");
          const a = document.createElement("a");
          a.href = "#";
          a.innerText = i;
          a.addEventListener("click", (e) => {
            e.preventDefault();
            fetchInquiryList(i);
          });
          li.append(a);
          pagination.append(li);
        }
      })
      .catch(err => console.error(err));
  };

  // 페이지 로드 시 목록 가져오기
  fetchInquiryList();
});
