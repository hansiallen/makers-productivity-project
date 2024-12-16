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

    // Message for delay whilst photo is uploading
    const updateForm = document.querySelector(".update-profile-container");
    const formSubmitBtn = document.querySelector(".submit-btn");

    updateForm.addEventListener("submit", profileUpdating);

    function profileUpdating(e) {
        e.preventDefault();
        formSubmitBtn.textContent = "Updating...";
        formSubmitBtn.disabled = true;
        this.submit();
    }
});