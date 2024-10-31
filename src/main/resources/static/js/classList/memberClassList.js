// 모달 열기
function openModal() {
    document.getElementById('deleteModal').style.display = 'block';
}

// 모달 닫기
function closeModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

// 삭제 확인
function confirmDeletion() {
    // 폼 제출 수행 (필요 시 추가 로직 삽입 가능)
    document.getElementById("myPageList").submit();
    closeModal();
}

// 모달 외부를 클릭하면 닫기
window.onclick = function (event) {
    const modal = document.getElementById('deleteModal');
    if (event.target == modal) {
        closeModal();
    }
}


// ----------------------------------------------------------------
document.getElementById("deleteBtn").addEventListener("click", function (event) {
  event.preventDefault();
  
  const selectedBoards = [];
  document.querySelectorAll('input[type="checkbox"]:checked').forEach(checkbox => {
      selectedBoards.push(checkbox.getAttribute("data-board-no"));
  });
  
  if (selectedBoards.length === 0) {
      alert("삭제할 강의를 선택해주세요.");
      return;
  }

  const confirmDelete = confirm("선택한 강의를 삭제하시겠습니까?");
  if (confirmDelete) {
      fetch("/member/memberClassList", {
          method: "PUT",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify({ boardNos: selectedBoards })
      })
      .then(response => response.json())
      .then(data => {
          if (data.success) {
              console.log("선택한 강의가 삭제되었습니다.");
              location.reload();
          } else {
              console.log(data.message || "삭제 중 오류가 발생했습니다.");
          }
      })
      .catch(error => {
          console.error("삭제 요청 중 오류:", error);
      });
  }
});
