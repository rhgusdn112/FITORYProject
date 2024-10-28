
// // ----------------------------------------------------------------------------------------------
// /* 강사 정보 상세조회 */
// const url = "/trainer/trainerDetail/{trainerNo:[0-9]+}";
// const trainerNoPattern = /^\/trainer\/trainerDetail\/([0-9]+)$/;

// // 정규식을 사용하여 URL에서 trainerNo 부분 추출
// const match = url.match(trainerNoPattern);

// document.getElementById("detailBtn").addEventListener("click", () => {

//   if (match) {
//     const trainerNo = match[1]; // 첫 번째 그룹 (숫자 부분)
//     console.log("trainerNo : ", trainerNo);
//   } else {
//     console.log("Error");
//     return;
//   }
// })
