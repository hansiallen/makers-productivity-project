document.addEventListener("DOMContentLoaded", function() {
    // Expand add contacts section
    const createEventForm = document.querySelector(".create-event-form");
    const addContactsBtn = document.querySelector(".event-add-contacts");
    const contactSearchContainer = document.querySelector(".event-contact-search-container");
    const selectedContacts = new Set();

    if (addContactsBtn) {
        addContactsBtn.addEventListener("click", showContacts)
    }

    function showContacts() {
        contactSearchContainer.classList.remove("hidden");
    }

    // Contacts dropdown
    const dropdownContent = document.querySelector(".dropdown-content");
    const dropdownInput = document.querySelector(".dropdown-input");

    if (dropdownInput) {
        dropdownInput.addEventListener("focus", () => {
            dropdownContent.classList.add("show");
            loadAllContacts();
        });

        dropdownInput.addEventListener("keyup", filterResults);
    }

    document.addEventListener("click", (e) => {
        if (!e.target.closest(".dropdown")) {
            dropdownContent.classList.remove("show");
        }
    });

    // For update events page, adding existing attendees to set so that they don't appear in search dropdown
    const contactInputs = document.querySelectorAll("input[name='contactIds']");
    contactInputs.forEach(input => selectedContacts.add(parseInt(input.value, 10)));

    async function filterResults() {
        const query = dropdownInput.value.toLowerCase().trim();

        try {
            let url = "/eventcontacts";
            if (query) {
                url += `?query=${encodeURIComponent(query)}`;
            }

            const response = await fetch(url);
            if (!response.ok) {
                throw new Error("Failed to fetch contacts");
            }

            const contacts = await response.json();
            const filteredContacts = contacts.filter(contact => !selectedContacts.has(contact.userId));

            updateDropdown(filteredContacts);
        } catch (error) {
            console.error("Error fetching contacts:", error);
        }
    }

    async function loadAllContacts() {
        try {
            const response = await fetch("/eventcontacts");
            if (!response.ok) {
                throw new Error("Failed to fetch contacts");
            }
            const contacts = await response.json();
            const filteredContacts = contacts.filter(contact => !selectedContacts.has(contact.userId));
            updateDropdown(filteredContacts);
        } catch (error) {
            console.error("Error fetching contacts:", error);
        }
    }

    function updateDropdown(contacts) {
        dropdownContent.innerHTML = "";

        if (contacts.length === 0) {
            dropdownContent.innerHTML = `<div class="dropdown-empty-msg" style="padding: 1em;">No contacts found</div>`;
            return;
        }

        contacts.forEach(contact => {
            const item = document.createElement("p");
            item.classList.add("dropdown-item");
            item.textContent = `${contact.firstName} ${contact.lastName}`;
            item.addEventListener("click", () => selectContact(contact));
            dropdownContent.appendChild(item);
        });
    }

    function selectContact(contact) {
        dropdownInput.value = "";
        dropdownContent.classList.remove("show");
        selectedContacts.add(contact.userId);

        const eventContactsDiv = document.querySelector(".event-contacts");
        const contactDiv = document.createElement("div");
        contactDiv.classList.add("event-contact");
        contactDiv.dataset.eventContactId = contact.userId;
        const contactName = document.createElement("p");
        const contactRemoveIcon = document.createElement("i");
        contactRemoveIcon.classList.add("fa-regular", "fa-circle-xmark");
        contactName.textContent = `${contact.firstName} ${contact.lastName}`;

        contactRemoveIcon.addEventListener("click", () =>  removeContactFromEvent(contact.userId));

        contactDiv.appendChild(contactName);
        contactDiv.appendChild(contactRemoveIcon);
        eventContactsDiv.appendChild(contactDiv);

        addContactToForm(contact.userId);
    }

    function addContactToForm(contactId) {
        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "contactIds";
        hiddenInput.value = contactId;
        hiddenInput.dataset.inputContactId = contactId;
        createEventForm.appendChild(hiddenInput);
    }

    function removeContactFromEvent(contactId) {
        const contactDiv = document.querySelector(`.event-contact[data-event-contact-id='${contactId}']`);
        if (contactDiv) {
            contactDiv.remove();
        }

        selectedContacts.delete(contactId);

        const hiddenInput = createEventForm.querySelector(`input[name='contactIds'][value='${contactId}']`);
        if (hiddenInput) {
            hiddenInput.remove();
        }
    }

    // Check end time after start time
    if (createEventForm) {
        createEventForm.addEventListener("submit", checkEventTimes);
    }

    function checkEventTimes(e) {
        const startTime = document.querySelector("input[name='startTime']").value;
        const endTime = document.querySelector("input[name='endTime']").value;

        if (endTime <= startTime) {
            e.preventDefault();
            alert("End time must be after start time.");
        }
    }

    // Remove contact from update form
    const existingRemoveIcons = document.querySelectorAll(".contact-remove");
    existingRemoveIcons.forEach(icon => {
        icon.addEventListener("click", function() {
            const contactDiv = icon.closest(".event-contact");
            const contactId = contactDiv.dataset.eventContactId;
            const eventId = contactDiv.dataset.eventId;
            removeContactFromEvent(contactId);
            removeContactFromDB(eventId, contactId);
        });
    });

    function removeContactFromDB(eventId, contactId) {
        fetch(`/${eventId}/${contactId}/status`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ status: "removed" })
        })
        .then(response => {
            if (!response.ok) {
                console.error('Error updating attendee status');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
})

