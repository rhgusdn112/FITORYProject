document.addEventListener("DOMContentLoaded", function() {
    const toggles = document.querySelectorAll(".faq-toggle");
    toggles.forEach(toggle => {
        toggle.addEventListener("click", function() {
            const answer = this.nextElementSibling;
            const icon = this.querySelector(".arrow-icon");

            // 현재 열린 답변이 있다면 닫기
            const openAnswer = document.querySelector(".supporting-text.open");
            if (openAnswer && openAnswer !== answer) {
                openAnswer.style.maxHeight = "0";
                openAnswer.style.padding = "0";
                openAnswer.classList.remove("open");
                const openIcon = openAnswer.previousElementSibling.querySelector(".arrow-icon");
                openIcon.classList.remove("open");
                openIcon.textContent = "▶"; // 오른쪽 화살표
            }

            // 클릭한 답변을 토글
            if (answer.classList.contains("open")) {
                answer.style.maxHeight = "0";
                answer.style.padding = "0";
                answer.classList.remove("open");
                icon.classList.remove("open");
                icon.textContent = "▶"; // 오른쪽 화살표
            } else {
                answer.style.maxHeight = answer.scrollHeight + "px";
                answer.style.padding = "10px 15px";
                answer.classList.add("open");
                icon.classList.add("open");
                icon.textContent = "▼"; // 아래쪽 화살표

                // 질문이 클릭되면 화면 상단에 질문이 완전히 보이도록 스크롤
                toggle.scrollIntoView({ behavior: "smooth", block: "start" });
            }
        });
    });
});