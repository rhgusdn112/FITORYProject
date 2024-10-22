// document.addEventListener('DOMContentLoaded', function () {
//   function submitLogin() {
//       const loginType = document.querySelector('input[name="loginType"]:checked').value;
//       if (loginType === 'member') {
//           window.location.href = '/member/login';
//       } else if (loginType === 'trainer') {
//           window.location.href = '/trainer/login';
//       }
//   }


//   // 로그인 버튼에 클릭 이벤트 할당
//   document.querySelector('.login-button').addEventListener('click', submitLogin);

//   // FITORY 로고 클릭 시 메인 페이지로 이동
//   const logoElement = document.querySelector('.logo h1');
//   if (logoElement) {
//       logoElement.addEventListener('click', function () {
//           window.location.href = '/main';
//       });
//   }
// });
document.getElementById('loginBtn').addEventListener('click', function() {
    const loginType = document.querySelector('input[name="loginType"]:checked').value;
    const form = document.getElementById('loginForm');

    // action 속성 변경
    if (loginType === 'member') {
        form.action = '/member/login';
    } else if (loginType === 'trainer') {
        form.action = '/trainer/login';
    }

    // 폼 제출
    form.submit();
});