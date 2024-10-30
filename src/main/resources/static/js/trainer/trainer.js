
// ----------------------------------------------------------------------------------------------
/* 강사 정보 상세조회 */
const url = "/trainer/trainerDetail/{trainerNo:[0-9]+}";
const trainerDetailNo = /^\/trainer\/trainerDetail\/([0-9]+)$/;

// 정규식을 사용하여 URL에서 trainerNo 부분 추출
const match = url.match(trainerDetailNo);

// 'watch' 버튼 클릭 시 처리
const watchBtn = document.getElementById("watch");
if (watchBtn) {
  watchBtn.addEventListener("click", () => {
    const trainerNoElement = document.getElementById('trainerNickname');
    const trainerNo = trainerNoElement ? trainerNoElement.getAttribute('data-trainerNo') : null;
    if (match) {
      const trainerNoFromUrl = match[1];
      console.log("trainerNo (from URL):", trainerNoFromUrl);
    } else if (trainerNo) {
      location.href = `/trainer/trainerVideoDetail/${trainerNo}`;
    } else {
      console.error("Error");
    }
  });
}