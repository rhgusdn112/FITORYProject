    // 라디오 버튼 클릭 시 내용 전환
    const memberRadio = document.getElementById('member');
    const trainerRadio = document.getElementById('trainer');
    const memberContent = document.getElementById('memberContent');
    const trainerContent = document.getElementById('trainerContent');

    memberRadio.addEventListener('click', () => {
        if (memberRadio.checked) {
            memberContent.style.display = 'block';
            trainerContent.style.display = 'none';
            signUpForm.action = "/member/signUp";
        }
    });

    trainerRadio.addEventListener('click', () => {
        if (trainerRadio.checked) {
            trainerContent.style.display = 'block';
            signUpForm.action = "/trainer/signUp";
        }
    });





//-----------------------------------------------------------------------
/**** 회원 가입 유효성 검사 ****/

/* 필수 입력 항목의 유효성 검사 여부 체크하기 위한 객체(체크리스트) */
const checkObj = {
  "email"    : false,
  "pw"       : false,
  "pwCheck": false,
  "tel" : false
};

/* ----- 이메일 유효성 검사 ----- */

// 1) 이메일 유효성 검사에 필요한 요소 얻어오기
const email = document.querySelector("#email");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일 메시지를 미리 작성
const emailMssageObj = {}; // 빈 객체
emailMssageObj.normal = "메일을 받을 수 있는 이메일을 입력해주세요";
emailMssageObj.invalid = "알맞은 이메일 형식으로 작성해 주세요";
emailMssageObj.duplication = "이미 사용중인 이메일입니다.";
emailMssageObj.check = "사용가능한 이메일입니다.";


// 3) 이메일이 입력된때 마다 유효성 검사 시행
email.addEventListener("input", e=>{
  
  //입력된 값 얻어오기
  const inputEmail = email.value.trim();

  // 4) 입력된 이메일이 없을 경우
  if(inputEmail.length == 0){

    // 이메일 메시지를 normal 상태 메시지로 변경
    emailMessage.innerText = emailMssageObj.normal;

    // #emailMessage에 색상 관련 클래스 모두 제거
    emailMessage.classList.remove("confirm", "error");

    // checkObj에서 Email을 false로 변경
    checkObj.email = false;

    email.value = ""; // 잘못 입력된 값

    return;
  }

  // 5) 이메일 형식이 맞는지 검사(정규표현식을 이용한 검사)

  // 이메일 형식 정규 표현식 객체
  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 입력 값이 이메일 형식이 아닌 경우
  if( regEx.test(inputEmail) === false ) {
    emailMessage.innerText = emailMssageObj.invalid; // 유효하지 않은 때 메시지
    emailMessage.classList.add("error"); // 빨간 글씨 추가
    emailMessage.classList.remove("confirm"); // 초록 글씨 삭제
    checkObj.Email = false; // 유효하지 않다고 체크
    return;
  }

  // 6) 이메일 중복 검사(Ajax)
  fetch("/member/emailCheck?email="+inputEmail)
  .then(Response => {
    if(Response.ok){ // HTTP 응답 상태 코드 200번(응답 성공)
      return Response.text(); // 응답결과를 text로 변환(파싱)
    }

    throw new Error("이메일 중복검사 에러");
  })
  .then(count =>{
    // 매개변수 count : 첫 번째 then에서 return된 값이 저장된 변수
    if( count == 1){ // 중복된 경우
      emailMessage.innerText = emailMssageObj.duplication; // 중복 메시지
      emailMessage.classList.add("error"); // 빨간 글씨 추가
      emailMessage.classList.remove("confirm"); // 초록 글씨 삭제
      checkObj.Email = false; // 유효하지 않다고 체크
      return;
    }

  // 중복이 아닌 경우
  emailMessage.innerText = emailMssageObj.check;
  emailMessage.classList.add("confirm"); 
  emailMessage.classList.remove("error");
  checkObj.email = true;

  })
  .catch( err => console.error(err));

});

//------------------------
/* 유효성 검사 - 전화번호 */

// 1) 전화번호 유효성 검사에 사용되는 요소 얻어오기
const tel = document.querySelector("#tel");
const telMessage = document.querySelector("#telMessage");

// 2) 전화번호 메시지 미리 작성
const telMessageObj = {}; // 빈 객체
telMessageObj.normal = "전화번호를 입력해주세요.(- 제외)";
telMessageObj.invalid = "알맞은 전화번호 형식으로 작성해 주세요";
telMessageObj.check = "사용가능한 전화번호입니다.";


// 3) 전화번호 입력될 때 마다 유효성 검사 시행
tel.addEventListener("input", ()=>{
  const inputTel = tel.value.trim();

  // 4) 입력된 전화번호 없을 경우
  if(inputTel.length == 0){

    // 전화번호 메시지를 normal 상태 메시지로 변경
    telMessage.innerText = telMessageObj.normal;

    // #telMessage 색상 관련 클래스 모두 제거
    telMessage.classList.remove("confirm", "error");

    // checkObj에서 Tel false로 변경
    checkObj.tel = false;

    tel.value = ""; // 잘못 입력된 값

    return;
  }

  // 5) 전화번호 형식이 맞는지 검사(정규표현식을 이용한 검사)

  // 전화번호 형식 정규 표현식 객체
  const regEx = /^010\d{8}$/; // 한글,영어,숫자로만 2~10글자

  // 입력 값이 전화번호 형식이 아닌 경우
  if( regEx.test(inputTel) === false ) {
    telMessage.innerText = telMessageObj.invalid; // 유효하지 않은 때 메시지
    telMessage.classList.add("error"); // 빨간 글씨 추가
    telMessage.classList.remove("confirm"); // 초록 글씨 삭제
    checkObj.tel = false; // 유효하지 않다고 체크
    return;
  }

   // 형식에 맞는 경우
  telMessage.innerText = telMessageObj.check;
  telMessage.classList.add("confirm"); 
  telMessage.classList.remove("error");
  checkObj.tel = true;

});


//------------------------
/* 유효성 검사 - 비밀번호 */

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

    // checkObj에서 pw false로 변경
    checkObj.pw = false;

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
    checkObj.pw = false; // 유효하지 않다고 체크
    return;
  }

  // 형식에 맞는 경우
  pwMessage.innerText = pwMessageObj.valid;
  pwMessage.classList.add("confirm"); 
  pwMessage.classList.remove("error");
  checkObj.pw = true;


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
    checkObj.pwCheck = true;
    return;
  }

  // 다른 경우
  pwMessage.innerText = pwMessageObj.error;
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  checkObj.pwCheck = false;
}


/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
pwCheck.addEventListener("input", ()=>{

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if( checkObj.pw === true ){
    checkpw();
    return;
  }


  // 비밀번호 input에 작성된 값이 유효하지 않은 경우
  checkObj.pw = false;
});

// ------------------------------------------------------------

/*----- 이메일 인증 -----*/

//[1] 인증 번호를 작성된 이메일로 발송하기

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

  checkObj.authKey = false; // 인증 안된 상태로 기록
  emailMessage.innerText = ""; // 인증 관련 메시지 삭제
  
  if(authTimer != undefined){
    clearInterval(authTimer); // 이전 인증 타이머 없애기
  }
  
  // 1) 작성된 이메일이 유효하지 않은 경우
  if(checkObj.email === false){
    alert("유효한 이메일 작성 후 클릭하세요");
    return;
  }

  //2) 비동기로 서버에서 작성된 이메일로 인증코드 발송(Ajax)
  fetch("/email/sendAuthKey", {
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
      checkObj.authKey = false;
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
      checkObj.authKey = false;
      return;
    }

    // 4) 일치하는 경우
    // - 타이머 멈춤
    clearInterval(authTimer);

    // + "인증 되었습니다" 화면에 초록색으로 출력
    authKeyMessage.innerText = "인증되었습니다."
    authKeyMessage.classList.add("confirm");
    authKeyMessage.classList.remove("error");

    checkObj.authKey = true; // 인증 완료
  })
  .catch(err => console.error(err));

});

// ---------------------------------------------
/* 회원가입 form 제출 시 전체 유효성 검사 여부 확인 */
const signUpForm = document.querySelector("#signUpForm");


signUpForm.addEventListener("submit", e => {

  // e.preventDefault(); // 기본 이벤트(form 제출) 막기

  // for(let key in 객체)
  // -> 반복마다 객체의 키 값을 하나씩 꺼내서 key 변수에 저장

  // 유효성 검사 체크리스트
  // fals인 경우가 있는지 검사
  for(let key in checkObj){ 

    if( checkObj[key] === false ){ // 유효하지 않은 경우
      let str; // 출력할 메시지 저장

      switch(key){
        case "email"     : str = "이메일이 유효하지 않습니다."; break;
        case "pw"        : str = "비밀번호가 유효하지 않습니다."; break;
        case "tel"       : str = "전화번호가 유효하지 않습니다."; break;
        case "authKey"         : str = "이메일 인증이 되지 않았습니다."; break;
      }

      alert(str); // 경고 출력

      // 유효하지 않은 요소로 focus 이동
      document.getElementById(key).focus();

      e.preventDefault(); // 제출 막기

      return;
    }
  }
})