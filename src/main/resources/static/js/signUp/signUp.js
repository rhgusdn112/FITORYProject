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

const telAuthKeyBtn = document.querySelector("#telAuthKeyBtn");

telAuthKeyBtn.addEventListener("click", () => {
  fetch("/send-sms/{memberTel}", {
    method : "post",
    headers : {"Content-Type" : "application/json"},
    body : memberTel
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("실패");
  })
  .then(result => {
    console.log(result);
  })
  .catch(err => console.error(err));

})