document.addEventListener("DOMContentLoaded", function() {
    const fileInput = document.querySelector('#profilePhoto');
    const photoTick = document.querySelector('.photo-tick');

    if (fileInput && photoTick) {
        fileInput.addEventListener('change', () => {
            if (fileInput.files && fileInput.files.length > 0) {
              photoTick.classList.remove('hidden');
            } else {
              photoTick.classList.add('hidden');
            }
        });
    }
});