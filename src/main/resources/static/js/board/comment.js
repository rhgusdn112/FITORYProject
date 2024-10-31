document.addEventListener('DOMContentLoaded', function () {

  // 등록 섹션 별점 요소
  const registrationStars = document.querySelectorAll('#registration-rating .fa-star'); // 등록용 별점 섹션 선택
  const editStars = document.querySelectorAll('#edit-rating .fa-star'); // 수정용 별점 섹션 선택
  const reviewTextareaRegistration = document.querySelector("#new-review-registration"); // 리뷰 등록용 텍스트 입력 요소
  const reviewTextareaEdit = document.querySelector("#new-review-edit"); // 리뷰 수정용 텍스트 입력 요소
  const submitButton = document.querySelector("#submit-review-btn"); // 리뷰 등록 버튼
  const editSubmitButton = document.querySelector("#edit-review-btn"); // 리뷰 수정 버튼
  const cancelButton = document.querySelector("#cancel-edit-review-btn"); // 리뷰 수정 취소 버튼
  const reviewContainerEdit = document.querySelector(".edit-review-section"); // 리뷰 수정 섹션
  

  // 별점 요소에 이벤트 리스너를 추가하는 함수
  function addStarEventListeners(stars, handleClick, handleHover, handleOut) {
    stars.forEach((star) => {
      star.addEventListener('click', handleClick); // 별 클릭 시 이벤트
      star.addEventListener('mouseover', handleHover); // 마우스 오버 시 이벤트
      star.addEventListener('mouseout', handleOut); // 마우스 아웃 시 이벤트
    });
  }

  // 사용자가 별점을 클릭했을 때 호출되는 함수
  function handleStarClick(star, stars) {
    const clickedRating = parseFloat(star.dataset.value);
    if (!isNaN(clickedRating)) {
      selectedRating = clickedRating; // 선택된 별점 업데이트
      updateStarRating(selectedRating, stars); // 별점 상태 업데이트
      console.log("Star clicked, selected rating:", selectedRating);
    }
  }

  // 사용자가 별점 위에 마우스를 올렸을 때 호출되는 함수
  function handleStarHover(star, stars) {
    updateStarRating(parseFloat(star.dataset.value), stars); // 별점 상태 업데이트 (미리보기)
  }

  // 사용자가 별점에서 마우스를 뗐을 때 호출되는 함수
  function handleStarOut(stars) {
    updateStarRating(selectedRating, stars); // 선택된 별점으로 다시 상태 업데이트
  }

  // 별점 상태를 업데이트하는 함수
  function updateStarRating(rating, stars) {
    console.log("Updating star rating to:", rating);
    stars.forEach((star) => {
      const value = parseFloat(star.dataset.value);
      star.classList.toggle('fa-solid', value <= rating); // 선택된 별점 이하를 채워진 별로 표시
      star.classList.toggle('fa-regular', value > rating); // 선택되지 않은 별은 비워진 별로 표시
    });
    document.querySelectorAll('.rating-value-display').forEach(display => {
      display.textContent = `${rating}점`; // 현재 선택된 별점을 화면에 표시
    });
  }

  // 리뷰 수정, 삭제, 제출 이벤트 핸들러를 설정하는 함수
  function attachEventHandlers() {
    document.querySelectorAll(".edit-review-btn").forEach(button => {
      button.addEventListener("click", () => handleReviewEdit(button)); // 리뷰 수정 버튼 클릭 시 이벤트 설정
    });

    document.querySelectorAll(".delete-review-btn").forEach(button => {
      button.addEventListener("click", () => handleReviewDelete(button)); // 리뷰 삭제 버튼 클릭 시 이벤트 설정
    });

    if (submitButton) {
      console.log("Submit button found and event handler attached");
      submitButton.addEventListener("click", handleReviewSubmit); // 리뷰 등록 버튼 클릭 시 이벤트 설정
    }

    if (cancelButton) {
      cancelButton.addEventListener("click", hideReviewContainer); // 리뷰 수정 취소 버튼 클릭 시 수정 섹션 숨기기
    }

    if (sortSelect) {
      sortSelect.addEventListener('change', () => {
        sortReviews(sortSelect.value); // 리뷰 정렬 옵션 변경 시 정렬 함수 호출
      });
    }
  }

  // 리뷰 수정 버튼 클릭 시 호출되는 함수
  function handleReviewEdit(button) {
    const reviewContent = button.dataset.content; // 기존 리뷰 내용 가져오기
    const reviewRating = button.dataset.rating; // 기존 리뷰 별점 가져오기
    const commentNo = button.dataset.id; // 리뷰 ID 가져오기

    console.log("Editing review:", { reviewContent, reviewRating, commentNo });

    if (reviewTextareaEdit) {
      reviewTextareaEdit.value = reviewContent || ""; // 기존 리뷰 내용을 수정 폼에 채우기
    }

    selectedRating = parseFloat(reviewRating) || 0; // 기존 별점 설정
    updateStarRating(selectedRating, editStars); // 수정 폼의 별점 상태 업데이트

    if (reviewContainerEdit) {
      reviewContainerEdit.classList.remove("hidden"); // 수정 섹션 표시
    }

    if (editSubmitButton) {
      editSubmitButton.replaceWith(editSubmitButton.cloneNode(true)); // 기존 이벤트 제거를 위해 버튼 복제
      document.querySelector("#edit-review-btn").addEventListener("click", () => {
        const updatedContent = reviewTextareaEdit.value; // 수정된 리뷰 내용 가져오기
        if (validateReviewInput(updatedContent, selectedRating)) {
          const requestBody = {
            commentNo: commentNo,
            reviewContent: updatedContent,
            number: selectedRating,
          };
          console.log("Sending request to update review:", requestBody);
          fetchRequest("/comment", "PUT", requestBody, "리뷰가 수정되었습니다.", "리뷰 수정에 실패했습니다.", true); // 서버에 수정된 리뷰 전송
        }
      });
    }
  }

  // 리뷰 수정 섹션을 숨기는 함수
  function hideReviewContainer() {
    if (reviewContainerEdit) {
      reviewContainerEdit.classList.add("hidden"); // 수정 섹션 숨기기
    }
    if (reviewTextareaEdit) {
      reviewTextareaEdit.value = ""; // 텍스트 초기화
    }
    selectedRating = 0; // 별점 초기화
    updateStarRating(selectedRating, editStars); // 수정 섹션 별점 초기화
  }

  // 리뷰 등록 버튼 클릭 시 호출되는 함수
  function handleReviewSubmit() {
    const reviewContent = reviewTextareaRegistration.value; // 등록할 리뷰 내용 가져오기
    console.log("Submitting review:", { reviewContent, selectedRating });
    if (validateReviewInput(reviewContent, selectedRating)) {
      const requestBody = {
        reviewContent: reviewContent,
        number: selectedRating,
        boardNo: location.pathname.split('/')[3] // 현재 페이지 경로에서 boardNo 가져오기
      };
      console.log("Sending request to submit review:", requestBody);
      fetchRequest("/comment", "POST", requestBody, "리뷰가 등록되었습니다.", "리뷰 등록에 실패했습니다.", true); // 서버에 리뷰 등록 요청 전송
    }
  }

  // 리뷰 삭제 버튼 클릭 시 호출되는 함수
  function handleReviewDelete(button) {
    const commentNo = button.dataset.id; // 삭제할 리뷰 ID 가져오기
    console.log("Deleting review, commentNo:", commentNo);
    if (!confirm("정말로 이 리뷰를 삭제하시겠습니까?")) return; // 삭제 확인
    const requestBody = {
      commentNo: commentNo
    };
    console.log("Sending request to delete review:", requestBody);
    fetchRequest("/comment", "DELETE", requestBody, "리뷰가 삭제되었습니다.", "리뷰 삭제에 실패했습니다.", true); // 서버에 리뷰 삭제 요청 전송
  }

  // 리뷰 입력값의 유효성을 검사하는 함수
  function validateReviewInput(content, rating) {
    console.log("Validating review input:", { content, rating });
    if (!content || rating <= 0) {
      alert("리뷰 내용과 별점을 모두 입력해주세요."); // 리뷰 내용이나 별점이 없을 경우 경고
      return false;
    }
    return true; // 유효한 경우 true 반환
  }

  // 서버 요청을 처리하는 공통 함수
  function fetchRequest(url, method, body, successMessage, failureMessage, reloadPage = false) {
    console.log("Sending fetch request:", { url, method, body });
    fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: body ? JSON.stringify(body) : null
    })
      .then(response => {
        console.log("Server response status:", response.status);
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then(data => {
        console.log("Server response data:", data);
        if (data && (data.result !== undefined ? data.result > 0 : true)) {
          alert(successMessage);
          if (reloadPage) {
            window.location.reload(); // 성공 시 페이지 새로고침
          }
        } else {
          alert(failureMessage);
        }
      })
      .catch(error => {
        console.error("Error:", error);
        alert(failureMessage); // 실패 시 경고 메시지
      });
  }

  // 등록 및 수정 섹션에 별점 이벤트 리스너 추가
  addStarEventListeners(registrationStars, (e) => handleStarClick(e.target, registrationStars), (e) => handleStarHover(e.target, registrationStars), () => handleStarOut(registrationStars));
  addStarEventListeners(editStars, (e) => handleStarClick(e.target, editStars), (e) => handleStarHover(e.target, editStars), () => handleStarOut(editStars));
  // 기타 이벤트 핸들러 추가
  attachEventHandlers();
});
