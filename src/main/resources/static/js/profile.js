$(document).ready(function()
{
    $('#edit-form').submit(function(e) {submitForm(e)})
});

function submitForm(e) {
    e.preventDefault()
    const $form = $("#edit-form");
    fetch($form.attr('action'), {
        method: "PATCH",
        body: $form.serialize()
    }).then(response => response.json()).then(createdObject => console.log(createdObject)).catch(console.error)
}