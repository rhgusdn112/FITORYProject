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
      const boardCount = document.createElement("a");

      boardCount.href = "/admin/board?trainerNo=" + trainer.trainerNo;
      
      boardCount.innerText = trainer.countBoardNo;
      td5.append(boardCount);

      const td6 = document.createElement("td");
      const reportCount = document.createElement("a");

      reportCount.href = "/admin/report?trainerNo=" + trainer.trainerNo;

      reportCount.innerText = trainer.trainerFlag;
      td6.append(reportCount);

      tr.append(td1, td2, td3, td4, td5, td6);

      trainerList.append(tr);
    })
  })
  .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded",()=>{
  selectTrainerList();
})