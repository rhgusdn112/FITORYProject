const pageChangeBtn = document.querySelector("#pageChangeBtn");

pageChangeBtn.addEventListener("click", () => {

  const form = document.querySelector("#loginForm");

  form.action = "/admin/login"

  form.submit();
});