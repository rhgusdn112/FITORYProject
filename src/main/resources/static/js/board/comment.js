document.addEventListener('DOMContentLoaded', function () {
  // 등록 섹션 별점 요소
  const registrationStars = document.querySelectorAll('#registration-rating .fa-star'); // 등록용 별점 섹션 선택
  // 수정 섹션 별점 요소
  const editStars = document.querySelectorAll('#edit-rating .fa-star'); // 수정용 별점 섹션 선택
  const reviewTextareaRegistration = document.querySelector("#new-review-registration");
  const reviewTextareaEdit = document.querySelector("#new-review-edit");
  const submitButton = document.querySelector("#submit-review-btn");
  const editSubmitButton = document.querySelector("#edit-review-btn");
  const cancelButton = document.querySelector("#cancel-edit-review-btn");
  const reviewContainerEdit = document.querySelector(".edit-review-section");
  let selectedRating = 0;

  // 등록 섹션 별 클릭, 마우스 오버/아웃 핸들러 설정
  registrationStars.forEach((star) => {
    star.addEventListener('click', () => handleStarClick(star, registrationStars));
    star.addEventListener('mouseover', () => updateStarRating(parseFloat(star.dataset.value), registrationStars));
    star.addEventListener('mouseout', () => updateStarRating(selectedRating, registrationStars));
  });

  // 수정 섹션 별 클릭, 마우스 오버/아웃 핸들러 설정
  editStars.forEach((star) => {
    star.addEventListener('click', () => handleStarClick(star, editStars));
    star.addEventListener('mouseover', () => updateStarRating(parseFloat(star.dataset.value), editStars));
    star.addEventListener('mouseout', () => updateStarRating(selectedRating, editStars));
  });

  // 리뷰 수정, 삭제 버튼 클릭 이벤트 핸들러 설정
  attachEventHandlers();

  function handleStarClick(star, stars) {
    const clickedRating = parseFloat(star.dataset.value);
    if (isNaN(clickedRating)) {
      console.error("Clicked star's data-value is not a number:", star.dataset.value);
      return;
    }
    selectedRating = clickedRating; // 선택한 별점을 설정
    updateStarRating(selectedRating, stars);
    console.log("Star clicked, selected rating:", selectedRating);
  }

  // 별점 업데이트 함수
  function updateStarRating(rating, stars) {
    console.log("Updating star rating to:", rating);
    stars.forEach((star) => {
      const value = parseFloat(star.dataset.value);
      star.classList.toggle('fa-solid', value <= rating);
      star.classList.toggle('fa-regular', value > rating);
    });
    document.querySelectorAll('.rating-value-display').forEach(display => {
      display.textContent = `${rating}점`;
    });
  }

  // 리뷰 수정, 삭제 버튼 클릭 핸들러 설정 함수
  function attachEventHandlers() {
    document.querySelectorAll(".edit-review-btn").forEach(button => {
      button.addEventListener("click", () => handleReviewEdit(button));
    });

    document.querySelectorAll(".delete-review-btn").forEach(button => {
      button.addEventListener("click", () => handleReviewDelete(button));
    });

    if (submitButton) {
      console.log("Submit button found and event handler attached");
      submitButton.addEventListener("click", handleReviewSubmit);
    }

    // 취소 버튼 클릭 시 수정 섹션 숨기기
    if (cancelButton) {
      cancelButton.addEventListener("click", hideReviewContainer);
    }
  }

  // 리뷰 수정 처리 함수
  function handleReviewEdit(button) {
    const reviewContent = button.dataset.content;
    const reviewRating = button.dataset.rating;
    const commentNo = button.dataset.id;

    console.log("Editing review:", { reviewContent, reviewRating, commentNo }); // 리뷰 수정 시 로그

    // 리뷰 작성 섹션에 데이터 채우기
    if (reviewTextareaEdit) {
      reviewTextareaEdit.value = reviewContent || "";  // 리뷰 내용 채우기
    }

    // 별점 설정
    selectedRating = parseFloat(reviewRating) || 0;
    updateStarRating(selectedRating, editStars);
    
    // 수정 섹션 보이도록 설정
    if (reviewContainerEdit) {
      reviewContainerEdit.classList.remove("hidden");  // 수정 섹션 보이게 하기
    }

    // 수정 버튼 클릭 시 이벤트 설정
    if (editSubmitButton) {
      editSubmitButton.replaceWith(editSubmitButton.cloneNode(true)); // 중복 방지를 위해 기존 이벤트 제거
      document.querySelector("#edit-review-btn").addEventListener("click", () => {
        const updatedContent = reviewTextareaEdit.value;
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
  }

  // 리뷰 수정 섹션 숨기기 함수
  function hideReviewContainer() {
    if (reviewContainerEdit) {
      reviewContainerEdit.classList.add("hidden"); // 섹션을 숨기기 위해 hidden 클래스 추가
    }
    if (reviewTextareaEdit) {
      reviewTextareaEdit.value = ""; // 텍스트 초기화
    }
    selectedRating = 0; // 별점 초기화
    updateStarRating(selectedRating, editStars); // 수정 섹션 별점 초기화
  }

  // 리뷰 등록 처리 함수
  function handleReviewSubmit() {
    const reviewContent = reviewTextareaRegistration.value;
    console.log("Submitting review:", { reviewContent, selectedRating }); // 리뷰 등록 시 로그
    if (validateReviewInput(reviewContent, selectedRating)) {
      const requestBody = {
        reviewContent: reviewContent,
        number: selectedRating,
        boardNo: location.pathname.split('/')[3]
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
      commentNo: commentNo
    };
    console.log("Sending request to delete review:", requestBody); // 리뷰 삭제 요청 로그
    fetchRequest("/comment", "DELETE", requestBody, "리뷰가 삭제되었습니다.", "리뷰 삭제에 실패했습니다.");
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
