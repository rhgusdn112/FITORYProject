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
