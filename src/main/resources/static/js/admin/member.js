const memberList = document.querySelector("#memberList");

const selectMemberList = () => {

  fetch("/admin/memberList")
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    memberList.innerHTML = "";

    list.forEach(member => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = member.memberNo;

      const td2 = document.createElement("td");
      td2.innerText = member.memberEmail;

      const td3 = document.createElement("td");
      td3.innerText = member.memberName;

      const td4 = document.createElement("td");
      td4.innerText = member.enrollDate;

      const td5 = document.createElement("td");
      const reportCount = document.createElement("a"); // 누적 신고 수 

      reportCount.innerText = member.memberFlag;
      td5.append(reportCount);

      reportCount.href = "/admin/report";

      const td6 = document.createElement("td");
      const activeBtn = document.createElement("button"); // 활동 내역

      activeBtn.innerText = "활동 내역";
      
      activeBtn.addEventListener("click", () => {

        location.href = "/admin/active?memberNo="+member.memberNo;
        
      })


      td6.append(activeBtn);
      
      tr.append(td1, td2, td3, td4, td5, td6);

      memberList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded",()=>{
  selectMemberList();
})


