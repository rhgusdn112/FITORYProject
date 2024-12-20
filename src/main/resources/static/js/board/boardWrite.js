/* 선택된 이미지 미리보기 관련 요소 모두 얻어오기 */

/* 
  [querySelector(), querySelectorAll()의 문제점]

  - 호출 되었을 시점의 요소 형태 그대로를 얻어옴

  [getElementsByClassName()]
  - 요소를 얻어와서 실시간으로 변화되는 상태를 계속 추적함  
*/

const previewList = document.getElementsByClassName("preview");
const inputImgList = document.getElementsByClassName("inputImage");
const deleteImgList = document.getElementsByClassName("delete-image");

// 마지막으로 선택된 파일을 저장할 변수
const lastValidFiles = [null];

/** 미리보기 함수
 * @param file : <input type = "file">에서 선택된 파일
 * @param order : 이미지 순서
 */
const updatePreview = (file, order)=>{
  console.log(order);
  
  // 선택된  파일이 지정된 크기를 초과한 경우 선택 막기
  const maxSize = 1024 * 1024 * 10; // 1MB를 byte 단위로 작성

  if(file.size > maxSize){ // 파일크기 초과 시
    alert("1MB 이하의 이미지만 선택해주세요");

    // 미리보기는 안되도 크기가 초과된 파일이 선택되어 있음!!!

    // 이전 선택된 파일이 없는데 크기를 초과한 파일을 선택한 경우
    if(lastValidFiles[order] === null){
      inputImgList[order].value = ""; // 선택 파일 삭제
      return;
    }
    
    // 이전 선택된 파일이 있을 때
    const dataTransfer = new DataTransfer();

    dataTransfer.items.add(lastValidFiles[order]);

    inputImgList[order].files = dataTransfer.files;

    return;
  }

  // 선택된 이미지 백업
  lastValidFiles[order] = file;

  // JS에서 제공하는 파읽을 읽어오는 객체
  const reader = new FileReader();

  // 파일을 읽어 오는데
  // DataURL 형식으로 읽어옴
  // DataURL: 파일 전체 데이터가 브라우저가 해석할 수 있는
  //          긴 주소형태 문자열로 변환
  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    previewList[order].src = e.target.result;
    // e.target.result == 파일이 변환된 주소 형태 문자열
  }) 
}



//-------------------------------------------------------------------

/* input태그, x버튼에 이벤트 리스너 추가 */
for(let i = 0; i < inputImgList.length; i++){
 
  // input 태그에 이미지 선택 시 미리보기 함수 호출
  inputImgList[i].addEventListener("change", e => {

    const file = e.target.files[0];

    if(file === undefined){ // 선택 취소 시

      // 이전에 선택한 파일이 "없는" 경우
      if(lastValidFiles[0] === null) return;

      //***  이전에 선택한 파일이 "있을" 경우 ***
      
      const dataTransfer = new DataTransfer();

      // DataTransfer가 가지고 있는 files 필드에 
      // lastVaildFiles[i] 추가 
      dataTransfer.items.add(lastValidFiles[0]);

      // input의 files 변수에 lastVaildFiles[i]이 추가된 files 대입

      inputImgList[i].files = dataTransfer.files;

      // 이전 선택된 파일로 미리보기 되돌리기
      updatePreview(lastValidFiles[i], i);

      return;
    }
    
    updatePreview(file, i);
  })


  /* x버튼 클릭 시 미리보기, 선택된 파일 삭제 */
   deleteImgList[0].addEventListener("click", () => {

    previewList[0].src    = ""; // 미리보기 삭제
    inputImgList[0].value = ""; // 선택된 파일 삭제
    lastValidFiles[0]     = null; // 백업 파일 삭제
   })


}// for end

/* 제목/내용 미작성 시 제출 불가 */
const form = document.querySelector("#boardWriteForm");
form.addEventListener("submit", e => {

  // 제목/내용 input 요소 얻어오기
  const boardTitle = document.querySelector("[name=boardTitle]");
  const boardContent = document.querySelector(".boardVideo");
  const boardSelect = document.querySelector(".boardSelect");
  
  if(boardSelect.value == "homeTraing"){
    form.action = "/board/2/insert";
  }else{
    form.action = "/board/3/insert";
  }

  if(boardTitle.value.trim().length === 0){
    alert("제목을 작성해주세요");
    boardTitle.focus(); // 제목으로 포커싱
    e.preventDefault(); // 제출 막기
    return;
  }

  if(boardContent.value.trim().length === 0){
    alert("동영상 경로를 확인해주세요");
    boardContent.focus(); // 주소으로 포커싱
    e.preventDefault(); // 제출 막기
    return;
  }

});

