/*Creates the Calendar with range picker, and restrict it.
The user may select up to 3 days of stay, and not after 30 days from today.
The days that already have reservations will be blocked*/
function getCalendar() {
    var datesOccupied
    $.ajax ({
        url : '/datesOccupied',
        type : 'get',
        dataType : 'json',
        success : function(response) {
            datesOccupied = response;
        },
        error : function(request, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });

    $('#stay').daterangepicker({
        "maxSpan": {
           "days": 2
        },
        "locale": {
           "format": "YYYY/MM/DD",
           "separator": " - ",
           "applyLabel": "Confirm",
           "cancelLabel": "Cancel",
           "fromLabel": "From",
           "toLabel": "To",
           "customRangeLabel": "Custom",
           "weekLabel": "W",
           "daysOfWeek": [
               "Su",
               "Mo",
               "Tu",
               "We",
               "Th",
               "Fr",
               "Sa"
           ],
           "monthNames": [
               "January",
               "February",
               "March",
               "April",
               "May",
               "June",
               "July",
               "August",
               "September",
               "October",
               "November",
               "December"
           ],
           "firstDay": 1
        },
        "minDate": moment().add(1, 'days'),
        "maxDate": moment().add(30, 'days'),
        "isInvalidDate": function(date) {
            $.map(datesOccupied, function(val, key) {
                console.log("key: " + key + " Value: " + val + " for Date: " + date);
                if (!(date.before(key) || date.after(val))) { return true; }
            });
            return false;
        }
    });
}
