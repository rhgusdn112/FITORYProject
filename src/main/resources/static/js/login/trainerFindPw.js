/*----- 이메일 인증 -----*/

//[1] 인증 번호를 작성된 이메일로 발송하기
let check = false;
// 인증 번호 받기 버튼
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

// 인증 관련 메시지 출력 span
const authKeyMessage = document.querySelector("#authKeyMessage");

const initTime = "05:00"; // 인증 초기 시간 지정
const initMin  = 4;  // 초기값 5분에 1초 감소된 후 분
const initSec  = 59; // 초기값 5분에 1초 감소된 후 초

// 실제 줄어든 시간(분/초)를 저장할 변수
let min = initMin;
let sec = initSec;

let authTimer; // 타이머 역할의 setInterval을 저장할 변수
            // -> 타이머를 멈추는 clearInterval 수행을 위해 필요

// 인증 번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {

  emailMessage.innerText = ""; // 인증 관련 메시지 삭제
  
  if(authTimer != undefined){
    clearInterval(authTimer); // 이전 인증 타이머 없애기
  }
  
  //2) 비동기로 서버에서 작성된 이메일로 인증코드 발송(Ajax)
  fetch("/email/sendFindPwAuthKey", {
    // 인증키 발송(POST 방식)
    // /email/sendAuthKey 요청을 처리하는 컨트롤러에
    // 입력된 이메일을 body에 담아서 제출
    method : "POST",
    headers : {"Content-Type" :"application/json"},
    body : email.value
  }) 
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패");
  })
  .then(result => {
    console.log(result);
  })
  .catch(err => console.error(err));

  /* 메일이 비동기로 보내지는 동안 아래 JS코드 수행 */

  // 3) 이메일 발송 메시지 출력 + 5분 타이머 출력
  alert("인증 번호가 발송 되었습니다");

  emailMessage.innerText = initTime; // 05:00 문자열 출력
  emailMessage.classList.remove("confirm", "error"); // 검정글씨

  // 1초가 지날 때마다 함수 내부 내용이 실행되는 setInterval 작성
  authTimer = setInterval(()=>{
    emailMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    if(min == 0 && sec == 0){
      clearInterval(authTimer);
      emailMessage.classList.add("error");
      emailMessage.classList.remove("confirm");
    }

    if(sec == 0){ // 출력된 초가 0인 경우(1분 지남)
      sec = 60;
      min--; // 분 감소
    }
    sec--; // 1초가 지날 때 마다 sec값 1씩 감소

  }, 1000);

});

/* 전달 받은 숫자 10미만(한 자리 수) 인 경우 
    앞에 0을 붙여 반환하는 함수 */
function addZero(num){
  if(num < 10) return "0" + num;
  else         return num;
};

//----------------------------------------------------------------

/* 인증 번호를 입력하고 인증하기 버튼을 클릭한 경우 */
const authKey = document.querySelector("#authKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

checkAuthKeyBtn.addEventListener("click", () => {

  // + (추가 조건) 타이머 00:00인 경우 버튼 클릭 막기
  if(min === 0 && sec === 0){
    alert("인증 번호 입력 제한 시간을 초과하였습니다");
    return;
  }

  // 1) 인증 번호 6자리가 입력이 되었는지 확인
  if(authKey.value.trim().length < 6){
    alert("인증 번호가 잘못 입력 되었습니다");
    return;
  }
  
  // 2) 입력된 이메일과 인증 번호를 비동기로 서버에 전달하여
  // Redis에 저장된 이메일, 인증번호와 일치하는지 확인

  // Ajax로 여러 데이터를 서버로 전달하고 싶을 땐
  // JSON 형태로 값을 전달해야 한다!

  // 서버로 제출할 데이터를 저장한 객체 생성
  const obj = {
    "email" : email.value, // 입력한 이메일
    "authKey" : authKey.value    // 입력한 인증번호
  };

  // JSON.stringify(객체) : 객체 -> JSON 변환(문자열화)

  fetch("/email/checkAuthKey", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("인증 실패....");
  })
  .then(result => {
    console.log("인증결과 : ", result);

    // 3) 일치하지 않는 경우
    if(result == false){
      alert("인증번호가 일치하지 않습니다");
      return;
    }

    // 4) 일치하는 경우
    // - 타이머 멈춤
    clearInterval(authTimer);

    // + "인증 되었습니다" 화면에 초록색으로 출력
    authKeyMessage.innerText = "인증되었습니다."
    authKeyMessage.classList.add("confirm");
    authKeyMessage.classList.remove("error");

    check = true; // 인증 완료
  })
  .catch(err => console.error(err));

});



// ---------------------------------------------
/* 회원가입 form 제출 시 전체 유효성 검사 여부 확인 */
const signUpForm = document.querySelector(".findPw");


signUpForm.addEventListener("click", () => {
  if( check === false ){ // 유효하지 않은 경우
    let str = "이메일을 작성하고 인증을 하세요";


    alert(str); // 경고 출력

    // 유효하지 않은 요소로 focus 이동
    document.getElementById(key).focus();

    e.preventDefault(); // 제출 막기

    return;
  }

  location.href = "/trainer/afterFindPw";
})