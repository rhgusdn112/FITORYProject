document.addEventListener('DOMContentLoaded', function() {
  const payButton = document.getElementById("payButton");

  const onClickPay = async () => {
      const amountInput = document.getElementById('amount');
      const nameInput = document.getElementById('name');
      const emailInput = document.getElementById('email');

      if (!amountInput || !nameInput || !emailInput || !title) {
          console.error('결제 정보가 올바르게 로드되지 않았습니다.');
          return;
      }

      // 결제 요청 팝업 호출 (카카오페이)
      window.open('/payment/kakaopay', '카카오페이 결제', 'width=500,height=600');
  };

  if (payButton) {
      payButton.addEventListener("click", onClickPay);
  } else {
      console.error('결제 버튼을 찾을 수 없습니다.');
  }
});