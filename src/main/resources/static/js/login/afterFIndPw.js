/* 유효성 검사 - 비밀번호 */
let check = false
// 1) 비밀번호 유효성 검사에 사용되는 요소 얻어오기
const pw = document.querySelector("#pw");
const pwCheck = document.querySelector("#pwCheck");
const pwMessage = document.querySelector("#pwMessage");

// 2) 비밀번호 메시지 미리 작성
const pwMessageObj = {}; // 빈 객체
pwMessageObj.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
pwMessageObj.invalid = "유효하지 않은 비밀번호 형식입니다.";
pwMessageObj.valid = "유효한 비밀번호 형식입니다.";
pwMessageObj.error = "비밀번호가 일치하지 않습니다.";
pwMessageObj.check = "비밀번호가 일치 합니다.";


// 3) 비밀번호 입력될 때 마다 유효성 검사 시행
pw.addEventListener("input", ()=>{
  const inputPw = pw.value.trim();

  // 4) 입력된 비밀번호 없을 경우
  if(inputPw.length == 0){

    // 비밀번호 메시지를 normal 상태 메시지로 변경
    pwMessage.innerText = pwMessageObj.normal;

    // #pwMessage 색상 관련 클래스 모두 제거
    pwMessage.classList.remove("confirm", "error");

    pw.value = ""; // 잘못 입력된 값

    return;
  }

  // 5) 비밀번호 형식이 맞는지 검사(정규표현식을 이용한 검사)

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
  pwMessage.innerText = pwMessageObj.valid;
  pwMessage.classList.add("confirm"); 
  pwMessage.classList.remove("error");


  // 비밀번호 확인이 작성된 상태에서
  // 비밀번호가 입력된 경우
  if(pwCheck.value.trim().length > 0){
    checkpw(); // 같은지 비교하는 함수
  }
});

/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
function checkpw(){

  // 같은 경우
  if(pw.value === pwCheck.value){
    pwMessage.innerText = pwMessageObj.check;
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    return;
  }

  // 다른 경우
  pwMessage.innerText = pwMessageObj.error;
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  check = true;
}


/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
pwCheck.addEventListener("input", ()=>{

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if( check === true ){
    checkpw();
    return;
  }


});

const signUpForm = document.querySelector(".changePw");


signUpForm.addEventListener("click", () => {
  if( check === false ){ // 유효하지 않은 경우
    const str = "입력값을 입력하세요";


    alert(str); // 경고 출력

    // 유효하지 않은 요소로 focus 이동
    document.getElementById(key).focus();

    e.preventDefault(); // 제출 막기

    return;
  }

  fetch("/afterFindId",{
    method : "post",
    headers : {"Content-Type":"application/json"},
    body : pw.value
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("실패");
  })
  .then(result => {
    console.log(result);
  })
  .catch(err => console.error(err));
  
})