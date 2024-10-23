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