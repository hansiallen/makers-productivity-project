document.addEventListener("DOMContentLoaded", function() {
    removeContactBtns = document.querySelectorAll(".contact-remove");

    removeContactBtns.forEach(btn => {
        btn.addEventListener("click", function() {
            const contactId = this.getAttribute("data-contact-id");

            fetch(`/favourites/${contactId}`, { method: 'POST'})
            .then(response => {
                if (response.ok) {
                    const contactCard = this.closest(".contact-card")
                    contactCard.remove();
                    const contactContainer = document.querySelector(".homepage-contacts-container");
                    if (!contactContainer || contactContainer.children.length === 0) {
                        contactContainer?.remove();
                        const emptyMessage = document.createElement("p");
                        emptyMessage.classList.add("homepage-empty-msg");
                        emptyMessage.textContent = "No favourite contacts.";
                        const section = document.querySelector(".homepage-contacts-section");
                        section.appendChild(emptyMessage);
                    }
                }
            })
            .catch(error => {
                console.error("Error removing contact: ", error);
            })
        })
    })

});