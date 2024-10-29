document.addEventListener("DOMContentLoaded", () => {
  // 프로필 이미지 요소들 가져오기
  const profileImgMain = document.querySelector("#profileImgMain");
  const profileImgMainSub = document.querySelector("#profileImgMainSub");
  const profileImgSub = document.querySelector("#profileImgSub");
  const profileImgSubSub = document.querySelector("#profileImgSubSub");

  // 이미지 입력 필드와 기타 버튼 요소들 가져오기
  const imageInput = document.querySelector("#imageInput");
  const deleteImage = document.querySelector("#deleteImage");
  const plus = document.querySelector("#plus");
  const updateButton = document.querySelector("#update");

  // 자격 사항
  const tbody = document.querySelector("#tbody");
  const plusButton = document.querySelector("#plus");


  let lastValidFile = null;
  let statusCheck = -1; // -1: 변경 없음, 1: 이미지 변경, 0: 이미지 삭제

    // plus 버튼 클릭 시 새로운 자격사항 행 추가
    plusButton?.addEventListener("click", () => {
      const tr = document.createElement("tr");

      // 자격사항 입력 셀 생성
      const td1 = document.createElement("td");
      const inputText = document.createElement("input");
      inputText.className = "review";
      inputText.name = 'qName';
      inputText.placeholder = "자격사항을 작성해주세요.";
      td1.appendChild(inputText);

      // 취득일 입력 셀 생성
      const td2 = document.createElement("td");
      const inputDate = document.createElement("input");
      inputDate.className = "date";
      inputDate.type = 'date';
      inputDate.name = 'qDate';
      td2.appendChild(inputDate);

      // tr 요소에 td1과 td2 추가
      tr.appendChild(td1);
      tr.appendChild(td2);

      // tbody에 새 행 추가
      tbody.appendChild(tr);
  });

  /* 프로필 이미지 미리보기 설정 */
  if (imageInput) {
      imageInput.addEventListener("change", (e) => {
          const file = e.target.files[0];
          if (file) {
              const reader = new FileReader();
              reader.onload = (event) => {
                  profileImgMain.src = event.target.result;
                  lastValidFile = file;
                  statusCheck = 1; // 이미지 변경 상태로 업데이트
                  console.log("이미지 미리보기 업데이트:", profileImgMain.src);
              };
              reader.readAsDataURL(file);
          } else {
              console.log("파일이 선택되지 않음");
          }
      });
  } else {
      console.error("#imageInput 요소가 없습니다.");
  }

  /* 새로운 자격사항 입력 필드 추가 */
  plus?.addEventListener("click", () => {
      const tr = document.createElement("tr");
      const td1 = document.createElement("td");
      const inputText = document.createElement("input");
      inputText.name = 'qName';
      inputText.placeholder = "자격사항을 작성해주세요.";

      const td2 = document.createElement("td");
      const inputDate = document.createElement("input");
      inputDate.name = 'qDate';
      inputDate.type = 'date';

      td1.append(inputText);
      td2.append(inputDate);
      tr.append(td1, td2);

      const tbody = document.querySelector("#tbody");
      tbody?.append(tr);
  });

  /* x 버튼 클릭 시 이미지 삭제 */
  deleteImage?.addEventListener("click", () => {
      profileImgMain.src = "/images/default-profile.png";
      profileImgMainSub.src = "/images/default-profile.png";
      profileImgSub.src = "/images/default-profile.png";
      profileImgSubSub.src = "/images/default-profile.png";
      imageInput.value = '';
      lastValidFile = null;
      statusCheck = 0; // 이미지 삭제 상태로 업데이트
      console.log("이미지가 기본값으로 초기화되었습니다.");
  });
});

window.onload = () => {
  // 프로필 이미지 요소들 가져오기
  const profileImgMain = document.querySelector("#trainerImgMain");
  const profileImgMainSub = document.querySelector("#trainerImgMainSub");
  const profileImgSub = document.querySelector("#trainerImgSub");
  const profileImgSubSub = document.querySelector("#trainerImgSubSub");

  // 이미지 입력 필드와 기타 버튼 요소들 가져오기
  const imageInput = document.querySelector("#imageInput");
  const deleteImage = document.querySelector("#deleteImage");

  let lastValidFile = null;
  let statusCheck = -1; // -1: 변경 없음, 1: 이미지 변경, 0: 이미지 삭제

  /* 프로필 이미지 미리보기 설정 */
  if (imageInput) {
      imageInput.addEventListener("change", (e) => {
          const files = e.target.files;
          const imgElements = [profileImgMain, profileImgMainSub, profileImgSub, profileImgSubSub];

          // 각 이미지 요소에 선택된 파일을 미리보기로 설정
          Array.from(files).forEach((file, index) => {
              if (file && imgElements[index]) {
                  const reader = new FileReader();
                  reader.onload = (event) => {
                      imgElements[index].src = event.target.result;
                      console.log(`이미지 ${index + 1} 미리보기 업데이트:`, imgElements[index].src);
                  };
                  reader.readAsDataURL(file);
                  statusCheck = 1; // 이미지 변경 상태로 업데이트
              }
          });

          lastValidFile = files[0];
      });
  } else {
      console.error("#imageInput 요소를 찾을 수 없습니다.");
  }

  /* x 버튼 클릭 시 이미지 삭제 */
  deleteImage?.addEventListener("click", () => {
      const defaultImage = "/images/default-profile.png";
      profileImgMain.src = defaultImage;
      profileImgMainSub.src = defaultImage;
      profileImgSub.src = defaultImage;
      profileImgSubSub.src = defaultImage;
      imageInput.value = '';
      lastValidFile = null;
      statusCheck = 0; // 이미지 삭제 상태로 업데이트
      console.log("이미지가 기본값으로 초기화되었습니다.");
  });
  document.querySelector("#myPageInfo")?.addEventListener("submit", (e) => {
    console.log("statusCheck:", statusCheck);

    if (statusCheck === -1) {
        console.log("변경 없음으로 제출 진행");
        return; // 변경 없음
    }

    if (statusCheck === 1 && profileImgMain.src.includes("default-profile.png")) {
        alert("이미지 변경 후 제출해주세요.");
        e.preventDefault();
        return;
    } else if (statusCheck === 0) {
        alert("이미지 삭제 후 제출해주세요.");
        e.preventDefault();
        return;
    }

    const qualifications = document.querySelectorAll("input[name='qName']");
    const hasEmptyQualification = Array.from(qualifications).some(input => input.value.trim() === '');

    if (hasEmptyQualification) {
        alert("모든 자격 사항을 입력해 주세요.");
        e.preventDefault();
    }
});
};


// --------------------------------------------------------------------------------------------------
/* 프로필 이미지 미리보기, 삭제하기 */
/* const profile = document.querySelector("#profile"); // 프로필
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

/* 프로필 화면에서 변경하기 버튼이 클릭된 후 
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
/* x 버튼 클릭 시 
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

  /* X 버튼 클릭 시 미리보기, 선택된 파일 삭제 
  deleteImageList[i].addEventListener("click", () => {
    previewList[i].src      = ""; 
    inputImageList[i].value = ""; 
    lastValidFiles[i]       = null; 
    if(orderList.includes(i)) {
      deleteOrderList.add(i);
    }
  })
} */