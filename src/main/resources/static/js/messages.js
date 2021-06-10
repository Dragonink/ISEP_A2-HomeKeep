$(document).ready(function () {
    $('#booking-form').submit(function (e) {
        submitForm(e)
    })
});

//TODO improve result display
function submitForm(e) {
    e.preventDefault()
    const $form = $("#booking-form");
    fetch($form.attr('action'), {
        method: "PATCH",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            throw "Error in booking request"
        }
    }).then(
        () => {
            alert("Answer sent")
            location.replace("/api/messages/inbox")
        }
    ).catch((e) => {
        alert(e)
    })
}
