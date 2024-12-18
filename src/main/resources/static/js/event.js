document.addEventListener("DOMContentLoaded", function() {
    const declineBtn = document.querySelector("#event-decline");
    const maybeBtn = document.querySelector("#event-maybe");
    const acceptBtn = document.querySelector("#event-accept");

    if (declineBtn) {
        declineBtn.addEventListener("click", function() {
            changeStatus('declined', declineBtn, maybeBtn, acceptBtn);
        });
        maybeBtn.addEventListener("click", function() {
            changeStatus('maybe', maybeBtn, declineBtn, acceptBtn);
        });
        acceptBtn.addEventListener("click", function() {
            changeStatus('accepted', acceptBtn, declineBtn, maybeBtn);
        });
    }

    function changeStatus(status, activeButton, inactiveButton1, inactiveButton2) {
        const eventId = activeButton.getAttribute("data-event-id");
        const attendeeId = activeButton.getAttribute("data-attendee-id");

        fetch(`/${eventId}/${attendeeId}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status: status }),
        })
        .then(response => {
            if (response.ok) {
                activeButton.classList.add("attendee-event-btn-selected");
                inactiveButton1.classList.remove("attendee-event-btn-selected");
                inactiveButton2.classList.remove("attendee-event-btn-selected");

                const statusText = document.querySelector(".user-event-status");
                statusText.textContent = status.charAt(0).toUpperCase() + status.slice(1);

                const attendeeStatuses = document.querySelectorAll('.attendee-event-status');
                attendeeStatuses.forEach(function (statusElement) {
                    const attendeeIdInElement = statusElement.getAttribute("data-contact-id");
                    if (attendeeIdInElement === attendeeId) {
                        statusElement.textContent = status.charAt(0).toUpperCase() + status.slice(1);
                    }
                });
            } else {
                console.error("Error updating status")
            }
        })
        .catch(error => {
            console.error("Error updating status: ", error);
        });
    }
})