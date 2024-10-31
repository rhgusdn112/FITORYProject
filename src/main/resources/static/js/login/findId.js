const email = document.querySelector("#email");
const submit = document.querySelector("#submit");

email.addEventListener("input", () =>{
  fetch("findId")
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("조회실패");
  })
  .then(email => {
    if(email != null){
      const a = document.createElement("a");
      a.innerText = "다음으로";
      a.href="/afterFindId";

      submit.append(a);
    }
  })
})
