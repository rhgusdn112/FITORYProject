// comment.js

document.addEventListener('DOMContentLoaded', function () {
  const stars = document.querySelectorAll('#review-rating .fa-star');
  let selectedRating = 0;

  // 별 클릭, 마우스 오버/아웃 핸들러 설정
  stars.forEach((star) => {
    star.addEventListener('click', () => handleStarClick(star));
    star.addEventListener('mouseover', () => updateStarRating(parseFloat(star.getAttribute('data-value'))));
    star.addEventListener('mouseout', () => updateStarRating(selectedRating));
  });

  // 리뷰 수정, 삭제 버튼 클릭 이벤트 핸들러 설정
  attachEventHandlers();

  // 초기 댓글 로드
  loadComments();

  function handleStarClick(star) {
    const clickedRating = parseFloat(star.getAttribute('data-value'));
    selectedRating = (clickedRating === selectedRating) ? 0 : clickedRating;
    updateStarRating(selectedRating);
  }

  // 별점 업데이트 함수
  function updateStarRating(rating) {
    stars.forEach((star) => {
      const value = parseFloat(star.getAttribute('data-value'));
      star.classList.toggle('fa-solid', value <= rating);
      star.classList.toggle('fa-regular', value > rating);
      star.classList.add('inline-star');
    });
    document.getElementById('rating-value-display').textContent = `${rating}점`;
  }

  // 리뷰 수정, 삭제 버튼 클릭 핸들러 설정 함수
  function attachEventHandlers() {
    document.querySelectorAll(".edit-review-btn").forEach(button => {
      button?.addEventListener("click", () => handleReviewEdit(button));
    });

    document.querySelectorAll(".delete-review-btn").forEach(button => {
      button?.addEventListener("click", () => handleReviewDelete(button));
    });

    document.querySelector("#submit-review")?.addEventListener("click", handleReviewSubmit);
  }

  // 리뷰 수정 처리 함수
  function handleReviewEdit(button) {
    const reviewContent = button.getAttribute("data-content");
    const reviewRating = button.getAttribute("data-rating");
    const reviewId = button.getAttribute("data-id");

    // 리뷰 작성 섹션에 데이터 채우기
    const reviewTextarea = document.querySelector("#new-review");
    const ratingStars = document.querySelectorAll("#review-rating i");

    // 리뷰 내용 채우기
    if (reviewTextarea) {
      reviewTextarea.value = reviewContent || "";
    }

    // 별점 설정
    selectedRating = parseFloat(reviewRating) || 0;
    updateStarRating(selectedRating);

    // 작성 섹션의 헤더 변경 (리뷰 작성 -> 리뷰 수정)
    const reviewHeader = document.querySelector("#review-section-header");
    if (reviewHeader) {
      reviewHeader.textContent = "리뷰 수정";
    }

    // 리뷰 수정 버튼 활성화 및 등록 버튼 숨기기
    const submitButton = document.querySelector("#submit-review");
    if (submitButton) {
      submitButton.style.display = "none";
    }

    let editButton = document.querySelector("#edit-review");
    if (!editButton) {
      editButton = document.createElement("button");
      editButton.id = "edit-review";
      editButton.classList.add("submit-review-btn");
      editButton.textContent = "리뷰 수정";
      submitButton.parentNode.appendChild(editButton);
    }
    editButton.style.display = "block";

    // 기존에 등록된 클릭 이벤트 핸들러 제거 후 새로운 핸들러 등록
    editButton.replaceWith(editButton.cloneNode(true));
    editButton = document.querySelector("#edit-review");
    editButton.addEventListener("click", function () {
      const updatedContent = reviewTextarea.value;
      const updatedRating = selectedRating;

      if (updatedContent && updatedRating) {
        const requestBody = {
          reviewNo: reviewId,
          reviewContent: updatedContent,
          rating: updatedRating
        };
        fetchRequest("/reviews/edit", "POST", requestBody, "리뷰가 수정되었습니다.", "리뷰 수정에 실패했습니다.");
      } else {
        alert("리뷰 내용과 별점을 모두 입력해주세요.");
      }
    });
  }

  // 리뷰 등록 처리 함수
  function handleReviewSubmit() {
    const reviewContent = document.querySelector("#new-review").value;
    const rating = selectedRating;

    if (reviewContent && rating) {
      const requestBody = {
        reviewContent: reviewContent,
        rating: rating
      };
      fetchRequest("/reviews/add", "POST", requestBody, "리뷰가 등록되었습니다.", "리뷰 등록에 실패했습니다.");
    } else {
      alert("리뷰 내용과 별점을 모두 입력해주세요.");
    }
  }

  // 리뷰 삭제 처리 함수
  function handleReviewDelete(button) {
    const commentNo = button.getAttribute("data-id");
    if (!confirm("정말로 이 리뷰를 삭제하시겠습니까?")) return;

    const requestBody = {
      reviewNo: commentNo,
      reviewDelFl: 'Y'
    };
    fetchRequest("/reviews/delete", "POST", requestBody, "리뷰가 삭제되었습니다.", "리뷰 삭제에 실패했습니다.");
  }

  // 서버 요청을 처리하는 공통 함수
  function fetchRequest(url, method, body, successMessage, failureMessage) {
    fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: body ? JSON.stringify(body) : null
    })
    .then(response => response.json())
    .then(data => {
      if (data > 0) {
        alert(successMessage);
        loadComments();
      } else {
        alert(failureMessage);
      }
    })
    .catch(error => console.error("Error:", error));
  }

  // 초기 댓글 로드 및 이벤트 핸들러 설정
  function loadComments() {
    fetch("/comment?boardNo=" + boardNo)
      .then(response => response.json())
      .then(data => {
        // 댓글을 렌더링하고 이벤트 핸들러를 다시 설정합니다.
        const commentsContainer = document.querySelector(".comments-container");
        commentsContainer.innerHTML = ""; // 기존 댓글 초기화
        data.forEach(comment => {
          // 댓글 요소 생성 및 추가 (여기서 간단히 가정)
          const commentElement = document.createElement("div");
          commentElement.classList.add("comment");
          commentElement.innerHTML = `<p>${comment.content}</p>`;
          commentsContainer.appendChild(commentElement);
        });
        attachEventHandlers();
      })
      .catch(error => console.error("Error:", error));
  }
});

function navigateInquiry(direction) {
  console.log(direction + ' 버튼 클릭됨');
  // TODO: API 호출 또는 데이터 처리 로직 추가
}

function completeInquiry() {
  console.log('답변 완료 버튼 클릭됨');
  // TODO: 완료 처리 시 서버에 업데이트 요청 및 UI 업데이트
}
