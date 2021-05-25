$(document).ready(function () {
    $('#edit-form').submit(function (e) {
        submitForm(e)
    })
});

//TODO improve display
function submitForm(e) {
    e.preventDefault()
    const $form = $("#edit-form");
    fetch($form.attr('action'), {
        method: "PATCH",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            throw "Unknown database error"
        }
    }).then(
        () => {
            alert("Account updated")
            //TODO need to update session UserDetails
        }
    ).catch((e) => {
        alert(e)
    })
}