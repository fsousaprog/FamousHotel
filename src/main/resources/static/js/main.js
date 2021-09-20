/*Creates the Calendar with range picker, and restrict it.
The days that already have reservations or that are out of range will be blocked*/
function getCalendar() {
    //Get the restrictions from the hidden field already initialized
    var restrictions = JSON.parse($('#restrictions')[0].value);
    var datesOccupied = restrictions.datesOccupied;

    //Create the Date picker already with the restrictions
    $('#stay').daterangepicker({
        "locale": {
           "format": restrictions.dateFormat.toUpperCase(),
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
           ]
        },
        "maxSpan": {
            "days": restrictions.maxSpan
        },
        "minDate": moment().add(restrictions.minAdvance, 'days'),
        "maxDate": moment().add(restrictions.maxAdvance, 'days'),
        "autoUpdateInput": false,
        "isInvalidDate": function(arg) {
            console.log("Dates occupied: " + datesOccupied);
            // Prepare the date comparison
            var thisMonth = arg._d.getMonth()+1;   // Months are 0 based
            if (thisMonth<10){
                thisMonth = "0"+thisMonth; // Leading 0
            }
            var thisDate = arg._d.getDate();
            if (thisDate<10){
                thisDate = "0"+thisDate; // Leading 0
            }
            var thisYear = arg._d.getYear()+1900;   // Years are 1900 based

            var thisCompare = thisYear+"/"+thisMonth+"/"+thisDate;

            if($.inArray(thisCompare,datesOccupied)!=-1){
                return true;
            }
            return false;
        }
    });

    //Format field and clear the date input
    $("#stay").on("apply.daterangepicker",function(e,picker) {
        var dateFormat = restrictions.dateFormat.toUpperCase();

        $(this).val(picker.startDate.format(dateFormat) + " - " + picker.endDate.format(dateFormat));

        // Get the selected bound dates.
        var startDate = picker.startDate.format(dateFormat)
        var endDate = picker.endDate.format(dateFormat)
        console.log(startDate + " to " + endDate);

        // Compare the dates again.
        var clearInput = false;
        for(i = 0; i < datesOccupied.length; i++){
            if(startDate < datesOccupied[i] && endDate > datesOccupied[i]){
                console.log("Found a disabled Date in selection!");
                clearInput = true;
                break;
            }
        }

        // If a disabled date is in between the bounds, clear the range.
        if(clearInput){
            $(this).val("").focus();
            console.log("Cleared the input field...");
            alert("Your range selection includes dates that already have a reservation!");
        }
    });

    $("#stay").on("cancel.daterangepicker", function(ev, picker) {
        $(this).val("");
    });

    $("#stay").click();
}