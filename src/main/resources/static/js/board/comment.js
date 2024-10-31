document.addEventListener('DOMContentLoaded', function () {
  const stars = document.querySelectorAll('#review-rating .fa-star');
  const reviewTextarea = document.querySelector("#new-review");
  const submitButton = document.querySelector("#submit-review");
  let selectedRating = 0;

  // 별 클릭, 마우스 오버/아웃 핸들러 설정
  stars.forEach((star) => {
    star.addEventListener('click', () => handleStarClick(star));
    star.addEventListener('mouseover', () => updateStarRating(parseFloat(star.dataset.value)));
    star.addEventListener('mouseout', () => updateStarRating(selectedRating));
  });

  // 리뷰 수정, 삭제 버튼 클릭 이벤트 핸들러 설정
  attachEventHandlers();

  function handleStarClick(star) {
    const clickedRating = parseFloat(star.dataset.value);
    selectedRating = (clickedRating === selectedRating) ? 0 : clickedRating;
    updateStarRating(selectedRating);
    console.log("Star clicked, selected rating:", selectedRating); // 별 클릭 시 선택된 별점 로그
  }

  // 별점 업데이트 함수
  function updateStarRating(rating) {
    console.log("Updating star rating to:", rating); // 별점 업데이트 로그
    stars.forEach((star) => {
      const value = parseFloat(star.dataset.value);
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

    submitButton?.addEventListener("click", handleReviewSubmit);
  }

  // 리뷰 수정 처리 함수
  function handleReviewEdit(button) {
    const reviewContent = button.dataset.content;
    const reviewRating = button.dataset.rating;
    const commentNo = button.dataset.id;

    console.log("Editing review:", { reviewContent, reviewRating, commentNo }); // 리뷰 수정 시 로그

    // 리뷰 작성 섹션에 데이터 채우기
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
    if (submitButton) {
      submitButton.style.display = "none";
    }

    let editButton = document.querySelector("#edit-review");
    if (!editButton) {
      editButton = document.createElement("button");
      editButton.id = "edit-review";
      editButton.classList.add("submit-review-btn");
      editButton.textContent = "리뷰 수정";
      if (submitButton && submitButton.parentNode) {
        submitButton.parentNode.appendChild(editButton);
      }
    }
    editButton.style.display = "block";
    editButton.replaceWith(editButton.cloneNode(true));

    document.querySelector("#edit-review").addEventListener("click", () => {
      const updatedContent = reviewTextarea.value;
      if (validateReviewInput(updatedContent, selectedRating)) {
        const requestBody = {
          commentNo: commentNo,
          reviewContent: updatedContent,
          number: selectedRating,
        };
        console.log("Sending request to update review:", requestBody); // 리뷰 업데이트 요청 로그
        fetchRequest("/comment", "PUT", requestBody, "리뷰가 수정되었습니다.", "리뷰 수정에 실패했습니다.");
      }
    });
  }

  // 리뷰 유효성 검증 함수
  function validateReviewInput(content, rating) {
    console.log("Validating review input:", { content, rating }); // 리뷰 유효성 검증 로그
    if (!content || rating <= 0) {
      alert("리뷰 내용과 별점을 모두 입력해주세요.");
      return false;
    }
    return true;
  }

  // 리뷰 등록 처리 함수
  function handleReviewSubmit() {
    const reviewContent = reviewTextarea.value;
    console.log("Submitting review:", { reviewContent, selectedRating }); // 리뷰 등록 시 로그
    if (validateReviewInput(reviewContent, selectedRating)) {
      const requestBody = {
        reviewContent: reviewContent,
        number: selectedRating
      };
      console.log("Sending request to submit review:", requestBody); // 리뷰 등록 요청 로그
      fetchRequest("/comment", "POST", requestBody, "리뷰가 등록되었습니다.", "리뷰 등록에 실패했습니다.");
    }
  }

  // 리뷰 삭제 처리 함수
  function handleReviewDelete(button) {
    const commentNo = button.dataset.id;
    console.log("Deleting review, commentNo:", commentNo); // 리뷰 삭제 시 로그
    if (!confirm("정말로 이 리뷰를 삭제하시겠습니까?")) return;

    const requestBody = {
      commentNo: commentNo,
      reviewDelFl: 'Y'
    };
    console.log("Sending request to delete review:", requestBody); // 리뷰 삭제 요청 로그
    fetchRequest("/comment", "DELETE", requestBody, "리뷰가 삭제되었습니다.", "리뷰 삭제에 실패했습니다.");
  }

  // 서버 요청을 처리하는 공통 함수
  function fetchRequest(url, method, body, successMessage, failureMessage) {
    console.log("Sending fetch request:", { url, method, body }); // 서버 요청 로그
    fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: body ? JSON.stringify(body) : null
    })
    .then(response => {
      console.log("Server response status:", response.status); // 서버 응답 상태 로그
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then(data => {
      console.log("Server response data:", data); // 서버 응답 데이터 로그
      if (data && (data.result !== undefined ? data.result > 0 : true)) {
        alert(successMessage);
        window.location.reload(); // 페이지 리프레시로 최신 상태 반영
      } else {
        alert(failureMessage);
      }
    })
    .catch(error => {
      console.error("Error:", error); // 오류 발생 시 로그
      alert(failureMessage);
    });
  }
});
