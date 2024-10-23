
/* 프로필 이미지 미리보기, 삭제하기 */
let statusCheck = -1;

// 마지막으로 선택괸 파일을 저장할 변수
let lastValidFile = null;

// 미리보기가 출력될 img
const profileImg = document.querySelector("#profileImg");

// 프로필 이미지를 선택할 input
const imageInput = document.querySelector("#imageInput");

// 기본 이미지로 변경할 x버튼
const deleteImage = document.querySelector("#deleteImage");

if(imageInput != null) { 
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
const profileForm = document.querySelector("#profile");
profileForm?.addEventListener("submit", e => {
  let flag = true; 
  if(statusCheck === -1) flag = true;
  if(loginMemberProfileImg === null && statusCheck === 1) flag = false;
  if(loginMemberProfileImg !== null && statusCheck === 0) flag = false;
  if(loginMemberProfileImg !== null && statusCheck === 1) flag = false;

  if(flag === true) {
    e.preventDefault();
    alert("이미지 변경 후 제출해주세요.");
  }
})