const ranges = [];

$(function () {
    getAvailableDates();
    const date_input = $(".input-daterange");
    date_input.datepicker({
        format: 'dd/mm/yyyy',
        beforeShowDay: function (date) {
            return isAvailable(date)
        }
    })
})

function isAvailable(date) {
    if (date < new Date()) return false;
    for (const range of ranges) {
        if (range[0] <= date && date <= range[1]) return true;
    }
    return false;
}

function getAvailableDates() {
    const bookings = $(".booking")
    bookings.each(function () {
        const startDate = $(this).find(".startDate").first()
        const endDate = $(this).find(".endDate").first()
        ranges.push([new Date(startDate.text()), new Date(endDate.text())])
    })
}