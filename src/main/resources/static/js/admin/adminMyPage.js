/**** 비밀번호 유효성 검사 ****/

// 유효성 검사 필요 객체 얻어오기
const updateBtn = document.querySelector("#updateBtn");
const password = document.querySelector("#password");
const pwConfirm = document.querySelector("#pwConfirm");
const pwMessage = document.querySelector("#pwMessage");
const pwCheck = document.querySelector("#pwCheck");

// 비밀번호 메시지 작성
const pwMessageObj = {}; // 빈 객체
pwMessageObj.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이로 입력하세요";
pwMessageObj.invalid = "유효하지 않은 비밀번호 형식입니다.";
pwMessageObj.error = "기존 비밀번호와 동일합니다.";
pwMessageObj.check = "유효한 비밀번호 형식입니다.";

const pwCheckobj = {};
pwCheckobj.normal = "변경할 비밀번호를 다시 입력하세요.";
pwCheckobj.invalid = "유효하지 않은 비밀번호 형식입니다.";
pwCheckobj.error = "변경할 비밀번호가 일치하지 않습니다.";
pwCheckobj.check = "변경할 비밀번호가 일치 합니다.";



// 비밀번호 입력될 때 마다 유효성 검사 시행
password.addEventListener("input", () => {
  const inputPw = password.ariaValueMax.trim();

  if(inputPw.length == 0){
    pwMessage.innerText = pwMessageObj.normal;
    
     // 비밀번호 메시지를 normal 상태 메시지로 변경
     pwMessage.innerText = pwMessageObj.normal;

     // #pwMessage 색상 관련 클래스 모두 제거
     pwMessage.classList.remove("confirm", "error");
 
     password.value = ""; // 잘못 입력된 값
 
     return;
  }

  // 비밀번호 형식 정규 표현식 객체
  const lengthCheck = inputPw.length >= 6 && inputPw.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(inputPw); // 영어 알파벳 포함
  const numberCheck = /\d/.test(inputPw); // 숫자 포함
  const specialCharCheck = /[!@#_-]/.test(inputPw); // 특수문자 포함

  // 조건이 하나라도 만족하지 못하면
  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
    pwMessage.innerText = pwMessageObj.invalid; // 유효하지 않은 때 메시지
    pwMessage.classList.add("error"); // 빨간 글씨 추가
    pwMessage.classList.remove("confirm"); // 초록 글씨 삭제
    return;
  }

  // 형식에 맞는 경우
  pwMessage.innerText = pwMessageObj.check;
  pwMessage.classList.add("confirm"); 
  pwMessage.classList.remove("error");

  // 비밀번호 확인에 입력된 값 가져오기
  const inputPwCheck = pwCheck.value.trim();

  // 비밀번호 확인이 작성된 상태에서
  // 비밀번호가 입력된 경우
  if(inputPwCheck.length > 0){
    checkPw(); // 같은지 비교하는 함수
    return;
  }

  // 비밀번호 확인 정규 표현식 객체
  const pwLengthCheck = inputPwCheck.length >= 6 && inputPwCheck.length <= 20;
  const pwLetterCheck = /[a-zA-Z]/.test(inputPwCheck); // 영어 알파벳 포함
  const pwNumberCheck = /\d/.test(inputPwCheck); // 숫자 포함
  const pwSpecialCharCheck = /[!@#_-]/.test(inputPwCheck); // 특수문자 포함

  if ( !(pwLengthCheck && pwLetterCheck && pwNumberCheck && pwSpecialCharCheck) ) {
    pwCheckobj.innerText = pwCheckobj.invalid; // 유효하지 않은 때 메시지
    pwCheckobj.classList.add("error"); // 빨간 글씨 추가
    pwCheckobj.classList.remove("confirm"); // 초록 글씨 삭제
    return;
  }
});

function checkPw(){

  // 같은 경우
  if(Pw.value === PwConfirm.value){
    pwCheckobj.innerText = pwCheckobj.check;
    pwCheckobj.classList.add("confirm");
    pwCheckobj.classList.remove("error");
    return;
  }

  // 다른 경우
  pwCheckobj.innerText = pwCheckobj.error;
  pwCheckobj.classList.add("error");
  pwCheckobj.classList.remove("confirm");
}


/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
PwConfirm.addEventListener("input", ()=>{

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if( checkObj.pw === true ){
    checkPw();
    return;
  }
});


updateBtn.addEventListener("click", () => {

});