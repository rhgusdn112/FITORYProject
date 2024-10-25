/* 버튼 클릭 시 테이블 생성 */
const plus = document.querySelector("#plus");
plus.addEventListener("click", () => {
  const tr = document.createElement("tr");
  const td1 = document.createElement("td");
  const inputText = document.createElement("input");
  inputText.name = 'qName';
  inputText.placeholder = "자격사항을 작성해주세요."
  const td2 = document.createElement("td");
  const inputDate = document.createElement("input");
  inputDate.name = 'qDate';
  inputDate.type = 'date'
  td1.append(inputText);
  td2.append(inputDate);
  tr.append(td1, td2);
  const tbody = document.querySelector("#tbody");
  tbody.append(tr);
});

/* 내 정보 수정 form 제출 시 */
const checkObj = {
  "trainerNickname" : true, "trainerTel" : true, "profileImg" : true
}
const update = document.querySelector("#update");
update?.addEventListener("submit", e => {
  for(let key in checkObj) { 
      if(checkObj[key] === false) { // 닉네임, 전화번호 중 유효하지 않은 값이 있을 경우
      let str = " 유효하지 않습니다.";
      switch(key){
        case "trainerrNickname" : str = "닉네임이" + str; break;
        case "trainerTel" : str = "전화번호가" + str; break;
        case "profileImg" : str = "이미지가" + str; break;
      }
      alert(str);
      e.preventDefault();
      document.getElementById(key).focus();
      return;
    }
  }
});

// --------------------------------------------------------------------------------------------------
/* 프로필 이미지 미리보기, 삭제하기 */
const profile = document.querySelector("#profile"); // 프로필
const profileImg = document.querySelector("#profileImg"); // 미리보기
const imageInput = document.querySelector("#imageInput"); // 프로필 이미지 선택 버튼
const deleteImage = document.querySelector("#deleteImage"); // x버튼

let statusCheck = -1;

// 마지막으로 선택괸 파일을 저장할 변수
let lastValidFile = null;

if(imageInput != null) { // 프로필 변경 화면인 경우 input 값 변경 시 미리보기 함수
  const updatePreview = (file) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    lastValidFile = file; 
    reader.addEventListener("load", e => { 
      profileImg.src = e.target.result;
      statusCheck = 1;
    })
  }
  imageInput.addEventListener("change", e => {
    const file = e.target.files[0];

    if(file === undefined) {
      if(lastValidFile === null) return;
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(lastValidFile);
      imageInput.files = dataTransfer.files;
      updatePreview(lastValidFile);
      return;
    }
    updatePreview(file);
  })
  deleteImage.addEventListener("click", () => {
    profileImg.src = userDefaultImage;
    imageInput.value = '';
    lastValidFile = null;
    statusCheck = 0;
  });
}

/* 프로필 화면에서 변경하기 버튼이 클릭된 후 */
const updateBtn = document.querySelector("#update");
updateBtn?.addEventListener("submit", e => {
  let flag = true;
  if(statusCheck === -1) flag = true;
  if(trainerLoginProfileImg === null && statusCheck === 1) flag = false;
  if(trainerLoginProfileImg !== null && statusCheck === 0) flag = false;
  if(trainerLoginProfileImg !== null && statusCheck === 1) flag = false;
  if(flag === true) {
    e.preventDefault();
    alert("이미지 변경 후 제출해주세요.");
  }
});

// -------------------------------------------------------------------------------------------------------------
/* x 버튼 클릭 시 */
const orderList = [];
const deleteOrderList = new Set();
const inputImageList = document.getElementsByClassName("inputImage");
const deleteImageList = document.getElementsByClassName("delete-image");
const lastValidFiles = [null, null, null, null];
const updatePreview = (file, order) => {
  const maxSize = 1024 * 1024 * 10; 
  if (file.size > maxSize) { 
    alert("10MB 이하의 이미지만 선택해주세요.");
    if (lastValidFiles[order] === null) { 
      inputImageList[order].value = ""; 
      return;
    }
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(lastValidFiles[order]);
    inputImageList[order].files = dataTransfer.files;
    return;
  }
  lastValidFiles[order] = file;
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.addEventListener("load", e => {
    previewList[order].src = e.target.result;
    deleteOrderList.delete(order);
  })
}
for (let i = 0; i < inputImageList.length; i++) {
  inputImageList[i].addEventListener("change", e => {
    const file = e.target.files[0];
    if (file === undefined) {
      if (lastValidFiles[i] === null) return;
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(lastValidFiles[i]);
      inputImageList[i].files = dataTransfer.files;
      updatePreview(lastValidFiles[i], i); 
      return;
    }
    updatePreview(file, i);
  })

  /* X 버튼 클릭 시 미리보기, 선택된 파일 삭제 */
  deleteImageList[i].addEventListener("click", () => {
    previewList[i].src      = ""; 
    inputImageList[i].value = ""; 
    lastValidFiles[i]       = null; 
    if(orderList.includes(i)) {
      deleteOrderList.add(i);
    }
  })
}