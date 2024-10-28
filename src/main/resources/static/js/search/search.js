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
});


    // 검색 입력 시 연관 검색어 표시
    const searchInput = document.getElementById("search-bar");
    const suggestionsBox = document.getElementById("suggestions");

    if (searchInput) {
        searchInput.addEventListener("input", () => {
            const keyword = this.value;
            if (keyword.length > 1) {
                fetch(`search/results?keyword=${keyword}`)
                    .then(response => response.json())
                    .then(data => {
                        suggestionsBox.innerHTML = "";
                        data.forEach(item => {
                            const div = document.createElement("div");
                            div.classList.add("suggestion-item");
                            div.textContent = item;
                            suggestionsBox.appendChild(div);
                        });
                    });
            } else {
                suggestionsBox.innerHTML = "";
            }
        });
    };
