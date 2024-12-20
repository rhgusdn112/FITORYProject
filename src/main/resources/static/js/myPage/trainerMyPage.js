/* + 버튼 클릭 시 테이블 생성 */
const plus = document.querySelector("#plus");
plus.addEventListener("click", () => {
  // 새로운 행(tr) 생성
  const tr = document.createElement("tr");

  // 자격 사항 입력 칸 생성
  const td1 = document.createElement("td");
  const inputText = document.createElement("input");
  inputText.name = 'qName';
  inputText.placeholder = "자격사항을 작성해주세요.";
  td1.appendChild(inputText);

  // 날짜 입력 칸과 삭제 버튼 생성
  const td2 = document.createElement("td");
  td2.style.display = "flex";
  td2.style.alignItems = "center";

  const inputDate = document.createElement("input");
  inputDate.name = 'qDate';
  inputDate.type = 'date';

  // 삭제 버튼 생성
  const removeBtn = document.createElement("button");
  removeBtn.textContent = "−"; // 마이너스 기호
  removeBtn.classList.add("qualificationRemovebtn");

  // 삭제 버튼 클릭 시 현재 행을 삭제하도록 설정
  removeBtn.addEventListener("click", (event) => {
    event.preventDefault(); // 폼이 제출되는 것을 방지
    tr.remove();
  });

  // td2에 날짜 입력란과 삭제 버튼 추가
  td2.append(inputDate, removeBtn);
  tr.append(td1, td2);

  // 테이블에 새 행 추가
  const tbody = document.querySelector("#tbody");
  tbody.appendChild(tr);
});

/* -버튼 클릭 시 */
const removeBtnList = document.querySelectorAll(".qualificationRemovebtn");

removeBtnList?.forEach(minus => {
  console.log(minus);

  minus.addEventListener("click", () => {
    minus.closest("tr").remove();
  })

})

/* 내 정보 수정 form 제출 시 */
const checkObj = {
  "trainerNickname": true, "trainerTel": true, "profileImg": true
}
const update = document.querySelector("#update");
update?.addEventListener("submit", e => {
  for (let key in checkObj) {
    if (checkObj[key] === false) { // 닉네임, 전화번호 중 유효하지 않은 값이 있을 경우
      let str = " 유효하지 않습니다.";
      switch (key) {
        case "trainerrNickname": str = "닉네임이" + str; break;
        case "trainerTel": str = "전화번호가" + str; break;
        case "profileImg": str = "이미지가" + str; break;
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
const previewList = document.querySelectorAll(".preview"); // 미리보기
const imageInput = document.querySelector("#imageInput"); // 프로필 이미지 선택 버튼
const deleteImage = document.querySelector("#deleteImage"); // x버튼

let statusCheck = -1

// 마지막으로 선택괸 파일을 저장할 변수
let lastValidFiles = null;



/* 프로필 이미지 미리보기 설정 */
if (imageInput) {
  imageInput.addEventListener("change", (e) => {
    const files = e.target.files;
    const imgElements = ["profileImgMain", "profileImgMainSub", "profileImgSub", "profileImgSubSub"];
    console.log(files);

    if (files.length === 0) {
      // 유지 코드
      const dataTransfer = new DataTransfer();

      // dataTransfer가 가지고 있는 files 필드에 lastValidFiles[i] 추가
      for (let f of lastValidFiles) {
        dataTransfer.items.add(f);
      }

      // input의 files 변수에 lastVauldFile이 추가된 files 대입
      imageInput.files = dataTransfer.files;

      return;
    }


    // 이미지 최소 2개
    if(files.length < 2){
      alert("2장 이상의 사진을 선택해주세요.");
      
      // 유지 코드
      const dataTransfer = new DataTransfer();

      // dataTransfer가 가지고 있는 files 필드에 lastValidFiles[i] 추가
      for (let f of lastValidFiles) {
        dataTransfer.items.add(f);
      }

      // input의 files 변수에 lastVauldFile이 추가된 files 대입
      imageInput.files = dataTransfer.files;

      return;
    }



    // 이미지 2개 초과 시 2개 까지만
    if(files.length > 2){
      const dataTransfer = new DataTransfer();
      
      for (let i = 0 ; i < 2 ; i++) {
        dataTransfer.items.add(files[i]);
      }

      lastValidFiles =  dataTransfer.files;
      imageInput.files = dataTransfer.files;
    } else{

      lastValidFiles = files;
    }


    previewList.forEach(item => item.removeAttribute("src"))

    Array.from(files).forEach((file, index) => {
      const reader = new FileReader();

      if (file && imgElements[index]) {
        reader.readAsDataURL(file);

        reader.addEventListener("load", e => {
          previewList[index].src = e.target.result;
          statusCheck = 1;
        })
        statusCheck = 1; // 이미지 변경 상태로 업데이트

      } else {
        console.error("사진을 찾을 수 없습니다.");
      }
    })

  });

}

document.getElementById("imageInput").addEventListener("change", function() {
  const fileName = this.files.length > 0 ? this.files[0].name : "선택된 파일 없음";
  document.getElementById("fileName").textContent = fileName;
});