document.addEventListener("DOMContentLoaded", () => {
    // 검색 버튼 클릭 시 검색 결과 페이지로 이동
    const searchButton = document.querySelector(".header-search-button");
    if (searchButton) {
        searchButton.addEventListener("click", function() {
            const searchKeyword = document.getElementById("search-bar").value;
            if (searchKeyword.trim() !== "") {
                window.location.href = `/search?keyword=${encodeURIComponent(searchKeyword)}`;
            }
        });
    }

    // 검색 입력 시 연관 검색어 표시
    const searchInput = document.getElementById("search-bar");
    const suggestionsBox = document.getElementById("suggestions");

    if (searchInput) {
        let debounceTimeout;

        searchInput.addEventListener("input", (event) => {
            clearTimeout(debounceTimeout);
            debounceTimeout = setTimeout(() => {
                const keyword = event.target.value;
                if (keyword.length > 1) {
                    fetch(`search/results?keyword=${encodeURIComponent(keyword)}`)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error("네트워크 응답이 실패했습니다.");
                            }
                            return response.json();
                        })
                        .then(data => {
                            suggestionsBox.innerHTML = "";
                            data.forEach(item => {
                                const div = document.createElement("div");
                                div.classList.add("suggestion-item");
                                div.textContent = item;
                                suggestionsBox.appendChild(div);
                            });
                        })
                        .catch(error => {
                            console.error("연관 검색어 불러오기 실패:", error);
                        });
                } else {
                    suggestionsBox.innerHTML = "";
                }
            }, 300);  // 300ms 지연
        });

        suggestionsBox.addEventListener("click", (event) => {
            if (event.target.classList.contains("suggestion-item")) {
                searchInput.value = event.target.textContent;
                suggestionsBox.innerHTML = ""; // 연관 검색어 목록 초기화
            }
        });
    }
});
