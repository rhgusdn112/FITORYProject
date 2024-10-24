// // 주문/결제 정보 로딩
// window.onload = function() {
//   const orderNo = getOrderNoFromURL();
//   if (orderNo) {
//       fetch(`/payment/order/${orderNo}`)
//           .then(response => response.json())
//           .then(data => {
//               document.getElementById('orderNo').innerText = data.orderNo;
//               document.getElementById('customerName').innerText = data.customerName;
//               document.getElementById('classTitle').innerText = data.classTitle;
//               document.getElementById('paymentAmount').innerText = data.paymentAmount;
//               document.getElementById('paymentStatus').innerText = data.status;
//           })
//           .catch(error => {
//               console.error('결제 정보 로드 중 오류 발생:', error);
//           });
//   }
// };

// // URL에서 주문 번호 추출
// function getOrderNoFromURL() {
//   const urlParams = new URLSearchParams(window.location.search);
//   return urlParams.get('orderNo');
// }

const paymentBtn = document.querySelector("#paymentBtn");
paymentBtn.addEventListener("click",()=>{
  const form = document.createElement("form");
  form.action = location.pathname;
  form.method = "POST";
  
  document.body.append(form);
  form.submit();
});