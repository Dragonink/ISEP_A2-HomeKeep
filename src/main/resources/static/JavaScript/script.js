function datePicker() {
    $(document).ready( function() {
        var dateFormat = "mm/dd/yy",
        from = $( "#from" )
            .datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1,
            showAnim: "fadeIn",
            minDate: 0
            })
            .on( "change", function() {
            to.datepicker( "option", "minDate", getDate( this ) );
            }),
        to = $( "#to" ).datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1,
            minDate: 0
        })
        .on( "change", function() {
            from.datepicker( "option", "maxDate", getDate( this ) );
        });
    
        function getDate( element ) {
        var date;
        try {
            date = $.datepicker.parseDate( dateFormat, element.value );
        } catch( error ) {
            date = null;
        }
    
        return date;
        }
    } );
}

function today(){
    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;

    var today = month + "/" + day + "/" + year;
    document.getElementById('from').value = today;      
    document.getElementById('to').value = today;
}   

function counter(){
    $(document).ready(function(){
        $("#plus").on("click", function(){
            var people = $("#voyagers").val()
            people = parseInt(people) + 1
            if(people >= 2){
                $("#minus").show()
            }
            $("#voyagers").val(people)
        }) 
        $("#minus").on("click", function(){
            var people = $("#voyagers").val()
            people = parseInt(people) - 1
            $("#voyagers").val(people)
            if(people < 2){
                $("#minus").hide()
            }
        })    
    })
}

function showFilters(){
    $(document).ready(function(){
        $("#checkboxes").toggle()
    })
}