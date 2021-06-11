$(document).ready(function () {
    $('.booking-status-form').submit(function (e) {
        submitStatusForm(e)
    })
    $('#new-message-form').submit(function (e) {
        submitMessageForm(e)
    })
});

function submitStatusForm(e) {
    e.preventDefault()
    const $form = $(".booking-status-form").first();
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
            location.reload()
        }
    ).catch((e) => {
        alert(e)
    })
}

function submitMessageForm(e) {
    e.preventDefault()
    const $form = $("#new-message-form");
    fetch($form.attr('action'), {
        method: "POST",
        body: new FormData(e.target)
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            throw "Error in message request"
        }
    }).then(
        () => {
            location.reload()
        }
    ).catch((e) => {
        location.reload()
    })
}
