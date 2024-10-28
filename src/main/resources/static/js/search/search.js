const searchInput = document.getElementById("search-bar");
const suggestionsBox = document.getElementById("suggestions");

searchInput.addEventListener("input", function() {
    const keyword = this.value;
    if (keyword.length > 1) {
        fetch(`/api/search/suggestions?keyword=${keyword}`)
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