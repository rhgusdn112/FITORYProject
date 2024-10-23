document.getElementById('loginBtn').addEventListener('click', function() {
  
  const form = document.getElementById('loginForm');

  // action 속성 변경
  form.action = '/member/login';
  
  // 폼 제출
  form.submit();
});