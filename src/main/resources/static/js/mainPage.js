/* 스크롤 이벤트 */
document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('.fullscreen-section');
    const footer = document.querySelector('.footer');
    const nav = document.createElement('div');
    let isScrolling = false;
    let currentSection = 0;
    let totalSections = sections.length + 1; // 푸터 포함

    // 초기 설정
    document.body.style.overflow = 'hidden';

    // 네비게이션 도트 생성 및 이벤트 처리
    nav.className = 'section-nav';
    for (let i = 0; i < totalSections; i++) {
        const dot = document.createElement('div');
        dot.className = 'nav-dot';
        dot.addEventListener('click', () => {
            if (isScrolling || currentSection === i) return;
            
            isScrolling = true;
            currentSection = i;
            
            if (i === sections.length) {
                // 마지막 도트(푸터)를 클릭한 경우
                showFooter();
            } else {
                // hideFooter();
                scrollToSection(i);
            }
            
            updateNavDots();
            setTimeout(() => isScrolling = false, 300);
        });
        nav.appendChild(dot);
    }

    // 바디 끝에 네비게이션 도트 추가
    document.body.append(nav);

    // 스크롤 이벤트 처리
    window.addEventListener('wheel', (e) => {
        // Ctrl 키가 눌린 경우 브라우저 기본 동작 허용 (확대/축소)
        if (e.ctrlKey) {
            return;
        }

        // Ctrl 키가 눌리지 않은 경우에만 기본 동작 방지
        e.preventDefault();
        
        if (isScrolling) return;
        isScrolling = true;
        
        if (e.deltaY > 0) { // 아래로 스크롤
            if (currentSection < totalSections - 1) {
                currentSection++;
                if (currentSection === sections.length) {
                    showFooter();
                } else {
                    scrollToSection(currentSection);
                }
            }
        } else { // 위로 스크롤
            if (currentSection > 0) {
                currentSection--;
                if (currentSection === sections.length - 1) {
                    // hideFooter();
                }
                scrollToSection(currentSection);
            }
        }
        
        updateNavDots();
        setTimeout(() => isScrolling = false, 300);
    }, { passive: false }); // 스크롤 이벤트 비활성화

    // 섹션 스크롤 함수
    function scrollToSection(index) {
        if (index < sections.length) {
            sections[index].scrollIntoView({ 
              behavior: 'smooth',
              block: 'end'
            });
        }
    }

    // Footer 표시 함수
    function showFooter() {
        footer.style.display = 'flex';
        setTimeout(() => {
            // footer.style.opacity = '1';
            footer.scrollIntoView({
               behavior: 'smooth', 
               block: 'end' });
        }, 50);
    }

    // 네비게이션 도트 업데이트
    function updateNavDots() {
        document.querySelectorAll('.nav-dot').forEach((dot, index) => {
            dot.classList.toggle('active', index === currentSection); // 현재 섹션에 대한 도트 활성화
        });
    }

    // 초기 상태 설정
    setTimeout(()=>{
      scrollToSection(0);
      updateNavDots();
    }, 100) 
});




/* 슬라이더 기능 구현 */
document.addEventListener('DOMContentLoaded', () => {
    // 슬라이더 기능 구현
    const sliders = document.querySelectorAll('.slider-container');
    
    sliders.forEach(slider => {
        const list = slider.querySelector('.class-list');
        const items = slider.querySelectorAll('.class-item');
        const leftBtn = slider.querySelector('.left-btn');
        const rightBtn = slider.querySelector('.right-btn');
        
        // 섹션별로 다른 itemsPerSlide 값 설정
        const isTrainerSection = slider.closest('#trainer-section') !== null;
        const itemsPerSlide = isTrainerSection ? 3 : 4; // 트레이너 섹션은 3개, 나머지는 4개
        
        let currentIndex = 0;
        const maxIndex = Math.ceil(items.length / itemsPerSlide) - 1;
        
        // 초기 설정
        setupSlider();
        updateButtons();
        
        // 오른쪽 버튼 클릭 이벤트
        rightBtn.addEventListener('click', () => {
            if (currentIndex < maxIndex) {
                currentIndex++;
                updateSlider();
            }
        });
        
        // 왼쪽 버튼 클릭 이벤트
        leftBtn.addEventListener('click', () => {
            if (currentIndex > 0) {
                currentIndex--;
                updateSlider();
            }
        });
        
        // 슬라이더 초기 설정
        function setupSlider() {
           // 각 아이템의 지정 (보이는 슬라이드 부분 (화면 너비의 90%), 1/6, 1/8)
            const slideWidth = isTrainerSection ? (90 / itemsPerSlide / 2) : (80 / itemsPerSlide / 2);
            
            // 컨테이너 스타일 설정
            list.style.display = 'flex';
            list.style.transition = 'transform 0.5s ease';
            list.style.width = `${(items.length / itemsPerSlide) * 100}%`;
            
            // 각 아이템 스타일 설정
            items.forEach(item => {
                item.style.flex = `0 0 ${slideWidth}%`;
                item.style.boxSizing = 'border-box';
                item.style.padding = '0 20px';
            });

            // 초기 위치 설정
            list.style.transform = 'translateX(0)';
        }
        
        // 슬라이더 위치 업데이트
        function updateSlider() {
            const slideWidth = 100 / items.length * itemsPerSlide;
            const offset = -(currentIndex * slideWidth);
            list.style.transform = `translateX(${offset}%)`;
            updateButtons();
        }
        
        // 버튼 상태 업데이트
        function updateButtons() {
            leftBtn.style.opacity = currentIndex === 0 ? '0.5' : '1';
            leftBtn.style.cursor = currentIndex === 0 ? 'default' : 'pointer';
            
            rightBtn.style.opacity = currentIndex === maxIndex ? '0.5' : '1';
            rightBtn.style.cursor = currentIndex === maxIndex ? 'default' : 'pointer';
        }
    });
});