function initForm() {
    /** @type {HTMLFormElement} */
    const FORM = document.querySelector("form#search-houses");

    /** @type {HTMLInputElement} */
    const endDateInput = FORM.elements.namedItem("endDate");
    function resetEndDateInput() {
        endDateInput.min = new Date().toISOString().split("T")[0];
    }
    resetEndDateInput();

    /** @type {HTMLInputElement} */
    const startDateInput = FORM.elements.namedItem("startDate");
    startDateInput.min = new Date().toISOString().split("T")[0];
    startDateInput.onchange = _event => {
        if (startDateInput.valueAsDate) {
            endDateInput.min = startDateInput.value;
        } else {
            resetEndDateInput();
        }
    };
    endDateInput.onchange = _event => {
        if (endDateInput.valueAsDate) {
            startDateInput.max = endDateInput.value;
        } else {
            startDateInput.max = null;
        }
    };
}
