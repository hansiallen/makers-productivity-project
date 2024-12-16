document.addEventListener("DOMContentLoaded", () => {
    const searchBox = document.getElementById("search-box")
    const results = document.getElementsByClassName("user-cont")
    const sections = document.getElementsByClassName("section")

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

    addEventListener("input", (event) => {
        filterSearch(searchBox.value)
        hideEmptyLabels()
    });

    const filterSearch = (query) => {
        Array.from(results).forEach((result) => {
            if(result.querySelector('h3').textContent.toLowerCase().includes(query.toLowerCase())) {
                result.style.display = "flex";
            } else {
                result.style.display = "none";
            }
        })
    }

    const hideEmptyLabels = () => {
        Array.from(sections).forEach((section) => {
            if(Array.from(section.getElementsByTagName('a')).every((element) => {return window.getComputedStyle(element, null).display == "none";})) {
                section.style.display = "none";
            } else {
                section.style.display = "flex";
            }
        })
    }

    hideEmptyLabels()
})