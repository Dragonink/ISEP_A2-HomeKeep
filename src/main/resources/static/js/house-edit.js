$(document).ready(function () {
    $('#house-form').submit(function (e) {
        submitForm(e);
    });
});

function submitForm(e) {
    e.preventDefault();
    const $form = $("#house-form");
    fetch($form.attr('action'), {
        method: "PATCH",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw "Error";
        }
    }).then(
        () => {
            alert("Home updated");
            location.reload();
        }
    ).catch((e) => {
        alert(e);
    });
}
