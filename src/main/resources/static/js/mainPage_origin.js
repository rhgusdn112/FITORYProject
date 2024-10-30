document.addEventListener('DOMContentLoaded', function () {
  const trainerImages = document.querySelectorAll('.trainer-image');
  
  trainerImages.forEach(image => {
      image.addEventListener('click', function () {
          const trainerId = image.getAttribute('data-id');
          if (trainerId) {
              window.location.href = `/trainer/detail/${trainerId}`;
          }
      });
  });
});
