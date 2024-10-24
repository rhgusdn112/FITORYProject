/* 내 정보 수정 form 제출 시 */
const checkObj = {
  "trainerNickname" : true, "trainerTel" : true
}
const update = document.querySelector("#update");
update?.addEventListener("submit", e => {
  for(let key in checkObj) { 
      if(checkObj[key] === false) { // 닉네임, 전화번호 중 유효하지 않은 값이 있을 경우
      let str = " 유효하지 않습니다.";
      switch(key){
        case "trainerrNickname" : str = "닉네임이" + str; break;
        case "trainerTel" : str = "전화번호가" + str; break;
      }
      alert(str); // 000이 유효하지 않습니다 출력
      e.preventDefault(); // form 제출 막기
      document.getElementById(key).focus(); // focus 맞추기
      return;
    }
  }
});

// --------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------
/* 프로필 이미지 미리보기, 삭제하기 */

// 프로필 이미지 업로드 상태에 따라 어떤 상태인지 구분하는 값
// -1 : 프로필 이미지를 바꾼적이 없음(초기 상태)
//  0 : 프로필 이미지 삭제(X버튼 클릭)
//  1 : 새 이미지 선택
let statusCheck = -1;

// 마지막으로 선택괸 파일을 저장할 변수
let lastValidFile = null;

// 미리보기가 출력될 img
const profileImg = document.querySelector("#profileImg");

// 프로필 이미지를 선택할 input
const imageInput = document.querySelector("#imageInput");

// 기본 이미지로 변경할 x버튼
const deleteImage = document.querySelector("#deleteImage");

if(imageInput != null) { // 프로필 변경 화면인 경우
  // input 값 변경 시 미리보기 함수
  /** 미리보기 함수
   * @param {*} file  : input type = "file"에서 선택된 파일
   */
  const updatePreview = (file) => {
    // JS에서 제공하는 파일을 읽어오는 객체
    const reader = new FileReader();
    // 파일을 읽어오는데 DataURL 형식으로 읽어옴
    // DateURL : 파일 전체 데이터가 브라우저가 해석할 수 있는 긴 주소형태 문자열로 변환
    reader.readAsDataURL(file);
    lastValidFile = file; // 선택된 파일을 lastValidFile에 대입(복사)
    reader.addEventListener("load", e => { // 선택된 파일이 다 인식 되었을 때
      profileImg.src = e.target.result;
      // e.target.result == 파일이 변환된 주소 형태 문자열
      statusCheck = 1; // 새 파일이 선택된 상태 체크
    })
  }
  imageInput.addEventListener("change", e => { // input type="file" 태그가 선택한 값이 변한 경우 수행
    // 파일이 선택된 1개를 얻어옴
    const file = e.target.files[0];

    // 선택된 파일이 없을 경우
    if(file === undefined) {
      // 이 전에 선택한 파일 유지하는 코드 -> 이 전에 선택한 파일을 저장할 전역 변수(lastValidFile) 선언

      // 이 전에 선택한 파일이 없는 경우 == 현재 페이지 들어와서 프로필 이미지 바꾼적이 없는 경우
      if(lastValidFile === null) return;

      // 이 전에 선택한 파일이 있을 경우
      const dataTransfer = new DataTransfer();

      // DataTransfer가 가지고 있는 files 필드에 lastValidFile 추가
      dataTransfer.items.add(lastValidFile);

      // input의 files 변수에 lastValidFile이 추가된 files 대입
      imageInput.files = dataTransfer.files;

      // 이 전에 선택된 파일로 미리보기(되돌리기)
      updatePreview(lastValidFile);
      return;
    }
    // 선택된 파일이 있을 경우
    updatePreview(file); // 미리보기 함수 호출
  })
  // X 버튼 클릭 시 기본 이미지로 변환
  deleteImage.addEventListener("click", () => {

    // 미리 보기를 기본 이미지로 변경
    profileImg.src = userDefaultImage;

    // input 태그와 마지막 선택된 파일을 저장하는 lastValidFile에 저장된 값을 모두 삭제
    imageInput.value = '';
    lastValidFile = null;
    statusCheck = 0; // 삭제 상태 체크
  });
}

/* 프로필 화면에서 변경하기 버튼이 클릭된 후 */
const profileForm = document.querySelector("#profile");
profileForm?.addEventListener("submit", e => {
  let flag = true; // true인 경우 제출 가능
  // 미변경 시 제출 불가
  if(statusCheck === -1) flag = true;

  // 기존 프로필 이미지 X -> 새 이미지 선택
  if(loginMemberProfileImg === null && statusCheck === 1) flag = false;

  // 기존 프로필 이미지 O -> X 버튼을 눌러서 삭제
  if(loginMemberProfileImg !== null && statusCheck === 0) flag = false;

  // 기존 프로필 이미지 O -> 새 이미지 선택
  if(loginMemberProfileImg !== null && statusCheck === 1) flag = false;

  if(flag === true) {
    e.preventDefault();
    alert("이미지 변경 후 제출해주세요.");
  }
});

// -------------------------------------------------------------------------------------------------------------

// 기존에 존재하던 이미지의 순서(order)를 기록할 배열
const orderList = []; // 0, 1

// X 버튼이 눌려져 삭제되는 이미지의 순서(order)를 기록하는 Set
const deleteOrderList = new Set();
// Set : 중복된 값을 저장 못하게 하는 객체(Java Set이랑 똑같음)
// Set을 사용하는 이유 : x 버튼이 눌러질 때 마다 중복되는 값을 저장 못하게 하기 위해서

// input type="file" 태그들
const inputImageList = document.getElementsByClassName("inputImage");

// x 버튼들
const deleteImageList = document.getElementsByClassName("delete-image");

// 마지막으로 선택된 파일을 저장할 배열
const lastValidFiles = [null, null, null, null, null];

/** 미리보기 함수
 * @param {*} file : <input type = "file"> 에서 선택된 파일
 * @param {*} order : 이미지 순서
 */
const updatePreview = (file, order) => {
  // 선택된 파일이 지정된 크기를 초과한 경우 선택 막기
  const maxSize = 1024 * 1024 * 10; // 1MB 를 byte단위로 작성
  if (file.size > maxSize) { // 파일 크기 초과 시
    alert("10MB 이하의 이미지만 선택해주세요.");

    // 미리보기는 안되도 크기가 초과된 파일이 선택되어 있음
    if (lastValidFiles[order] === null) { // 이전 선택된 파일이 없는데 크기 초과 파일을 선택한 경우
      inputImageList[order].value = ""; // 선택 파일 삭제
      return;
    }
    // 이전에 선택된 파일이 있을 때
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(lastValidFiles[order]);
    inputImageList[order].files = dataTransfer.files;
    return;
  }
  // 선택된 이미지 백업
  lastValidFiles[order] = file;
  // JS에서 제공하는 파읽을 읽어오는 객체
  const reader = new FileReader();

  // 파일을 읽어 오는데 DataURL 형식으로 읽어옴
  // DataURL: 파일 전체 데이터가 브라우저가 해석할 수 있는 긴 주소형태 문자열로 변환
  reader.readAsDataURL(file);
  reader.addEventListener("load", e => {
    previewList[order].src = e.target.result; // e.target.result == 파일이 변환된 주소 형태 문자열

    // 이미지가 성공적으로 읽어진 경우 deleteOrderList에서 해당 이미지 순서를 삭제
    // 이유 : 이 전에 x 버튼을 눌러 삭제 기록이 있을 수 있기 때문에
    deleteOrderList.delete(order);
  })
}



/* input태그, x버튼에 이벤트 리스너 추가 */
for (let i = 0; i < inputImageList.length; i++) {

  // input 태그에 이미지 선택 시 미리보기 함수 호출
  inputImageList[i].addEventListener("change", e => {
    const file = e.target.files[0];

    if (file === undefined) { // 선택 취소 시

      // 이전에 선택한 파일이 없는 경우
      if (lastValidFiles[i] === null) return;

      // 이전에 선택한 파일이 "있을" 경우
      const dataTransfer = new DataTransfer();

      // DataTransfer가 가지고 있는 files 필드에 
      // lastValidFiles[i] 추가 
      dataTransfer.items.add(lastValidFiles[i]);

      // input의 files 변수에 lastVaildFile이 추가된 files 대입
      inputImageList[i].files = dataTransfer.files;

      // 이전 선택된 파일로 미리보기 되돌리기
      updatePreview(lastValidFiles[i], i); 
      return;
    }
    updatePreview(file, i);
  })

  /* X 버튼 클릭 시 미리보기, 선택된 파일 삭제 */
  deleteImageList[i].addEventListener("click", () => {
    previewList[i].src      = ""; // 미리보기 삭제
    inputImageList[i].value = ""; // 선택된 파일 삭제
    lastValidFiles[i]       = null; // 백업 파일 삭제

    // 기존에 존재하던 이미지가 있는 상태에서 x 버튼이 눌러졌을 때
    // -> 기존에 이미지가 있었는데 i번째 이미지 x 버튼 눌러서 삭제
    if(orderList.includes(i)) {
      deleteOrderList.add(i);
    }
  })
}