document.addEventListener("DOMContentLoaded", function() {
    const removeNotificationBtn = document.querySelectorAll(".notification-remove");
    removeNotificationBtn.forEach(btn => {
        btn.addEventListener("click", function () {
            const notificationId = this.getAttribute("data-notification-id");

            fetch(`/notifications/${notificationId}`, { method: 'POST' })
                .then(response => {
                    if (response.ok) {
                        const notificationCard = this.closest(".notification-card");
                        notificationCard.remove();

                        // Check if the container is empty and display an empty message if needed
                        const notificationContainer = document.querySelector(".homepage-notifications-container");
                        if (!notificationContainer || notificationContainer.children.length === 0) {
                            notificationContainer?.remove();
                            const emptyMessage = document.createElement("p");
                            emptyMessage.classList.add("homepage-empty-msg");
                            emptyMessage.textContent = "No new notifications.";
                            const section = document.querySelector(".homepage-notifications-section");
                            section.appendChild(emptyMessage);
                        }
                    }
                })
                .catch(error => {
                    console.error("Error removing notification: ", error);
                });
        });
    });
})