$(document).ready(function () {
    $('#addHome-form').submit(function (e) {
        submitForm(e)
    })
});

function submitForm(e) {
    e.preventDefault()
    const $form = $("#addHome-form");
    fetch($form.attr('action'), {
        method: "POST",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            throw "Error"
        }
    }).then(
        () => {
            alert("Home added")
            location.replace("/index")
        }
    ).catch((e) => {
        alert(e)
    })
}