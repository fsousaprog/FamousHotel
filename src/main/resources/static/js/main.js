/*Creates the Calendar with range picker, and restrict it.
The user may select up to 3 days of stay, and not after 30 days from today.
The days that already have reservations will be blocked*/
function getCalendar() {
    //Get dates already reserved from the controller
    var datesOccupied
    $.ajax ({
        url : "/datesOccupied",
        type : "get",
        dataType : "text",
        success : function(response) {
            datesOccupied = response.split(",");
        },
        error : function(request, textStatus, errorThrown) {
            alert("Error trying to get Calendar: " + textStatus + " " + errorThrown);
        }
    });

    //Create the Date picker already with the restrictions
    $('#stay').daterangepicker({
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
           ]
        },
        "maxSpan": {
            "days": 2
        },
        "minDate": moment().add(1, "days"),
        "maxDate": moment().add(30, "days"),
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
            console.log("Formatted date to compare: " + thisCompare);

            if($.inArray(thisCompare,datesOccupied)!=-1){
                console.log("      ^--------- DATE FOUND HERE");
                return true;
            }
            return false;
        }
    });

    //Clear the date input and format
    $("#stay").on("apply.daterangepicker",function(e,picker) {
        $(this).val(picker.startDate.format("YYYY/MM/DD") + " - " + picker.endDate.format("YYYY/MM/DD"));

        // Get the selected bound dates.
        var startDate = picker.startDate.format("YYYY/MM/DD")
        var endDate = picker.endDate.format("YYYY/MM/DD")
        console.log(startDate + " to " + endDate);

        // Compare the dates again.
        var clearInput = false;
        for(i=0;i<datesOccupied.length;i++){
            if(startDate<datesOccupied[i] && endDate>datesOccupied[i]){
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
}