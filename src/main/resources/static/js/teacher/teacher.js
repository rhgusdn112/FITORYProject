document.querySelectorAll('.profile img').forEach(function(img) {
  img.addEventListener('click', function() {
      const teacherName = this.alt; // 이미지의 alt 속성을 사용하여 이름을 가져옴
      window.location.href = `/teacher/${teacherName}`;
  });
});
