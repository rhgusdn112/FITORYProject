document.getElementById('payButton').addEventListener('click', function() {
  const paymentData = {
      amount: document.getElementById('amount').value,
      name: document.getElementById('name').value,
      email: document.getElementById('email').value
  };

  // PortOne 결제 요청 로직
  fetch('/payments/request', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(paymentData)
  }).then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('결제가 성공적으로 완료되었습니다.');
        } else {
            alert('결제에 실패했습니다. 다시 시도해주세요.');
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('결제 처리 중 오류가 발생했습니다.');
    });
});