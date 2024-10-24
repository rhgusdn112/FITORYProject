const password = document.querySelector("#password");
const loginBtn = document.querySelector("#loginBtn");
const passBtn = document.querySelector("#passBtn");

document.getElementById("loginBtn").addEventListener("click", e => {
  const form = document.getElementById("form");

  if (!password === "") {
    alert("비밀번호를 입력해주세요");
    return;
  } else if (!inputPw === password) {
    alert("비밀번호가 맞지 않습니다.");
    return;
  } else {
    if (inputPw === "trainerPw") {
      form.action = "/trainer/trainerMyPage";
  } else if (inputPw === "memberPw") {
      form.action = "/trainer/trainerMyPage";
  }
  form.submit();
  }
});
