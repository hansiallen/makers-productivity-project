document.addEventListener("DOMContentLoaded", function() {
    // Add or remove contact as favourite using the star icon on the profile page
    profileFavouriteIcon = document.querySelector(".profile-favourite-icon");

    if (profileFavouriteIcon) {
        profileFavouriteIcon.addEventListener("click", function() {
            const contactId = this.getAttribute("data-contact-id");
            const isFavourited = this.querySelector("i").classList.contains("fa-solid");

            const url = isFavourited ? `/favourites/remove/${contactId}` : `/favourites/add/${contactId}`;

            fetch(url, { method: 'POST'})
            .then(response => {
                if (response.ok) {
                    const icon = this.querySelector("i");
                    icon.classList.toggle("fa-regular");
                    icon.classList.toggle("fa-solid");
                }
            })
            .catch(error => {
                console.error("Error updating contact: ", error);
            })
        })
    }

    // Remove a contact as a favourite from the home page
    const removeContactBtns = document.querySelectorAll(".contact-remove");
    removeContactBtns.forEach(btn => {
        btn.addEventListener("click", function () {
            const contactId = this.getAttribute("data-contact-id");

            fetch(`/favourites/remove/${contactId}`, { method: 'POST' })
                .then(response => {
                    if (response.ok) {
                        const contactCard = this.closest(".contact-card");
                        contactCard.remove();

                        // Check if the container is empty and display an empty message if needed
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
                });
        });
    });

    const modal = document.querySelector("[data-modal]")
    const closeBtn = document.querySelector("[data-close-modal]")
    const modalForm = document.querySelector("[data-modal-form]")

    closeBtn.addEventListener("click", () => {
        modal.close()
    })

    window.removeConnectionModal = (event, id) => {
        event.preventDefault()
        modal.showModal()
        modalForm.action = `/contact/remove/${id}`
    }
});