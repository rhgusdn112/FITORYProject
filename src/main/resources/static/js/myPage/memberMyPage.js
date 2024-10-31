const myPageInfo = document.querySelector("#myPageInfo");
const updateBtn = document.querySelector("#updateBtn");

const checkObj = {
  "memberName": true, "memberTel": true
}
const update = document.querySelector("#update");
update?.addEventListener("submit", e => {
  for (let key in checkObj) {
    if (checkObj[key] === false) { // 닉네임, 전화번호 중 유효하지 않은 값이 있을 경우
      let str = " 유효하지 않습니다.";
      switch (key) {
        case "memberName": str = "이름이" + str; break;
        case "memberTel": str = "전화번호가" + str; break;
      }
      alert(str);
      e.preventDefault();
      document.getElementById(key).focus();
      return;
    }
  }
});
