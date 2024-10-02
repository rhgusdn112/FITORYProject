const login = document.querySelector(".nav-item");

login.addEventListener("click", e=>{
  fetch("/login")
  .then(response => {
    if(response.ok) return response.json();

    throw new Error("로그인 실패");
  })
  .then(loginInfo => {
    



  })

});