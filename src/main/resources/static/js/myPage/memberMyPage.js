const myPageInfo = document.querySelector("#myPageInfo");
const updateBtn = document.querySelector("#updateBtn");
const nomal = document.querySelectorAll("[name = memberNomal]");
const check = document.querySelectorAll("[name = memberCheck]");

/* 회원 정보 수정 */
updateBtn.addEventListener("click", () => {
  if (nomal === false) {
    alert("필수 작성 칸이 비어있습니다.");
    return;
  } else {
    alert("수정되었습니다.");
    return;
  }
});

// ---------------------------------------------------------
// 중복검사 