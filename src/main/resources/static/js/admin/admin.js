const memberList = document.querySelector("#memberList");

const selectMemberList = () => {

  fetch("/admin/member")
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    console.log(list);
    memberList.innerHTML = "";

    list.forEach(member => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const th1 = document.createElement("th");
      th1.innerText = member.memberNo;

      const td2 = document.createElement("td");
      td2.innerText = member.memberEmail;

      const th3 = document.createElement("th");
      th3.innerText = member.memberName;

      const th4 = document.createElement("th");
      th4.innerText = member.enrollDate;

      const th5 = document.createElement("th");
      th5.innerText = member.countComment;

      const th6 = document.createElement("th");
      th6.innerText = member.memberFlag;

      tr.append(th1, td2, th3, th4, th5, th6);

      memberList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded",()=>{
  selectMemberList();
})