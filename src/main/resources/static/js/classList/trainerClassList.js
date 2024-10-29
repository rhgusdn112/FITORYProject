const classList = document.querySelector("#classList");

const selectClassList = () => {
  fetch("/trainer/classList",{
    method : "get",
    headers : {"Content-Type":"application/json"},
    body : trainerLogin.trainerNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 실패");
  })
  .then(list => {
    //  console.log(list);

     classList.innerText = "";

     list.forEach(class => {
      
      const tr1 = document.createElement("tr");
      const th1 = document.createElement("th");
      th1.rowSpan = "3";
      
      const th2 = document.createElement("th");
      th2.innerHTML = class.thumnail;

      const th3 = document.createElement("th");
      const input = document.createElement("input")
      input.type = "checkbox";

      th3.append(input);

      tr1.append(th1, th2, th3);
     })
  })
  .catch(err => console.error(err));
}