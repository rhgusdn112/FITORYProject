document.addEventListener("DOMContentLoaded", () => {
  // 프로필 이미지 요소
  const profileImgMain = document.querySelector("#profileImgMain");
  const profileImgMainSub = document.querySelector("#profileImgMainSub");
  const profileImgSub = document.querySelector("#profileImgSub");
  const profileImgSubSub = document.querySelector("#profileImgSubSub");

  // 이미지 입력 필드, 기타 버튼 요소
  const imageInput = document.querySelector("#imageInput");
  const deleteImage = document.querySelector("#deleteImage");
  const plus = document.querySelector("#plus");
  const updateBtn = document.querySelector("#update");

  // 자격 사항 요소
  const tbody = document.querySelector("#tbody");
  const plusBtn = document.querySelector("#plus");


  let lastValidFile = null;
  let statusCheck = -1; // -1: 변경 없음, 1: 이미지 변경, 0: 이미지 삭제

  updateBtn?.addEventListener("submit", e => {
    let flag = true;
    if(statusCheck === -1) flag = true;
    if(profileImgMain === null && statusCheck === 1) flag = false;
    if(profileImgMainSub !== null && statusCheck === 0) flag = false;
    if(profileImgSub !== null && statusCheck === 1) flag = false;
    if(profileImgSubSub !== null && statusCheck === 1) flag = false;
    if(flag === true) {
      e.preventDefault();
      alert("이미지 변경 후 제출해주세요.");
    }
  });

    // plus 버튼 클릭 시 새로운 자격사항 행 추가
    plusBtn?.addEventListener("click", () => {
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
      profileImgMain.src = "/images/**";
      profileImgMainSub.src = "/images/**";
      profileImgSub.src = "/images/**";
      profileImgSubSub.src = "/images/**";
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
      const defaultImage = "/images/**";
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

    if (statusCheck === 1 && profileImgMain.src.includes("/images/**")) {
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
