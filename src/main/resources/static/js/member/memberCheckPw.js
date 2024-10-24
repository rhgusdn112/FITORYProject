const password = document.querySelector("#password");
const loginBtn = document.querySelector("#loginBtn");
const passBtn = document.querySelector("#passBtn");
loginBtn.addEventListener("click", () => {
  const inputPw = password.value.trim();
  if (!password === "") {
    alert("비밀번호를 입력해주세요");
    return
  } else if (!inputPw === password) {
    alert("비밀번호가 맞지 않습니다.");
    return;
  } else {
    fetch("/myPage/memberMyPage", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(password)
    })
      .then(response => {
        if (response.ok) return response.text();
        throw new Error("비밀번호 인증에 실패하였습니다.");
      })
      .then(inputType => {
        console.log(inputType);
      })
      .catch(err => console.error(err));
  }
})