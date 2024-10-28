document.addEventListener("DOMContentLoaded", function() {
  // 리뷰 작성 버튼 클릭 시
  const submitReviewBtn = document.getElementById("submit-review");
  if (submitReviewBtn) {
    submitReviewBtn.addEventListener("click", function() {
      const reviewContent = document.getElementById("new-review").value;
      const reviewRating = document.getElementById("review-rating").value;

      if (!reviewContent.trim()) {
        alert("리뷰 내용을 입력하세요.");
        return;
      }

      // 리뷰 작성 시 서버에 보낼 요청 데이터를 구성합니다.
      const requestBody = {
        reviewContent: reviewContent,
        number: reviewRating,
        boardNo: boardNo, // 현재 게시글 번호
      };

      // 리뷰 작성 요청을 서버로 보냅니다. POST 메서드를 사용하여 새로운 리뷰를 생성합니다.
      fetch("/comment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody)
      })
      .then(response => response.json())
      .then(data => {
        if (data > 0) {
          alert("리뷰가 등록되었습니다.");
          loadComments();
        } else {
          alert("리뷰 등록에 실패했습니다.");
        }
      })
      .catch(error => console.error("Error:", error));
    });
  }

  // 리뷰 수정, 삭제 버튼 클릭 이벤트 핸들러 설정
  function attachEventHandlers() {
    document.querySelectorAll(".edit-review-btn").forEach(button => {
      if (button) {
        button.addEventListener("click", function() {
          const commentNo = this.getAttribute("data-id");
          const reviewContent = prompt("수정할 리뷰 내용을 입력하세요.");

          if (!reviewContent) {
            return;
          }

          // 리뷰 수정 시 서버에 보낼 요청 데이터를 구성합니다.
          const requestBody = {
            commentNo: commentNo,
            reviewContent: reviewContent
          };

          // 리뷰 수정 요청을 서버로 보냅니다. PUT 메서드를 사용하여 기존 리뷰의 내용을 업데이트합니다.
          fetch("/comment", {
            method: "PUT",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify(requestBody)
          })
          .then(response => response.json())
          .then(data => {
            if (data > 0) {
              alert("리뷰가 수정되었습니다.");
              loadComments();
            } else {
              alert("리뷰 수정에 실패했습니다.");
            }
          })
          .catch(error => console.error("Error:", error));
        });
      }
    });

    document.querySelectorAll(".delete-review-btn").forEach(button => {
      if (button) {
        button.addEventListener("click", function() {
          const commentNo = this.getAttribute("data-id");

          if (!confirm("정말로 이 리뷰를 삭제하시겠습니까?")) {
            return;
          }

          // 리뷰 삭제 요청을 서버로 보냅니다. DELETE 메서드를 사용하여 특정 리뷰를 삭제합니다.
          fetch("/comment", {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({ commentNo: commentNo })
          })
          .then(response => response.json())
          .then(data => {
            if (data > 0) {
              alert("리뷰가 삭제되었습니다.");
              loadComments();
            } else {
              alert("리뷰 삭제에 실패했습니다.");
            }
          })
          .catch(error => console.error("Error:", error));
        });
      }
    });
  }

  // 초기 댓글 로드 및 이벤트 핸들러 설정
  loadComments();
});
