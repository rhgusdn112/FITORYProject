const trainerList = document.querySelector("#trainerList");

const selectTrainerList = () => {

  fetch("/admin/trainerList")
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    // console.log(list);
    trainerList.innerText="";

    list.forEach(trainer => {

      // tr 요소 만들기
      const tr = document.createElement("tr");

      const td1 = document.createElement("td");
      td1.innerText = trainer.trainerNo;

      const td2 = document.createElement("td");
      td2.innerText = trainer.trainerEmail;

      const td3 = document.createElement("td");
      td3.innerText = trainer.trainerNickname;

      const td4 = document.createElement("td");
      td4.innerText = trainer.enrollDate;

      const td5 = document.createElement("td");
      td5.innerText = trainer.trainnerDelFl;

      const td6 = document.createElement("td");
      const reportCount = document.createElement("a");
      
      reportCount.href = "/admin/report?trainerNo=" + trainer.trainerNo;
      
      reportCount.innerText = trainer.countReport;
      td6.append(reportCount);
      
      const td7 = document.createElement("td");
      const activeBtn = document.createElement("button");
      
      activeBtn.innerText = "활동내역 조회";
      
      activeBtn.addEventListener("click", () => {

        location.href = "/admin/trainerActive?trainerNo="+ trainer.trainerNo;
        
      })

      td7.append(activeBtn);
      
      const td8 = document.createElement("td");
      const changeBtn = document.createElement("button");
      changeBtn.innerText = "탈퇴상태 변경";

      changeBtn.addEventListener("click", () => {
        fetch("/trainer/changeStatus",{
          method : "put",
          headers : {"Content-Type" : "application/json"},
          body : trainer.trainerNo
        })
        .then(response => {
          if(response.ok) return response.json();
          throw new Error("수정 실패");
        })
        .then(result => {
          if(result > 0) return selectTrainerList();
        })
        .catch(err => console.error(err));
      })

      td8.append(changeBtn);
      
      tr.append(td1, td2, td3, td4, td5, td6, td7, td8);
      trainerList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded",()=>{
  selectTrainerList();
})