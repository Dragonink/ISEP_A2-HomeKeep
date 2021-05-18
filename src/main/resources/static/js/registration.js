$(document).ready(function () {
    $('#registration-form').submit(function (e) {
        submitForm(e)
    })
});

//TODO improve result display
function submitForm(e) {
    e.preventDefault()
    const $form = $("#registration-form");
    fetch($form.attr('action'), {
        method: "POST",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            throw "Email address already used"
        }
    }).then(
        () => {
            alert("Account registered")
            location.replace("/profile")
        }
    ).catch((e) => {
        alert(e)
    })
}
