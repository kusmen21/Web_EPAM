$(document).ready(function() {
    $(".setting_date").change(function(){
        var $dateFrom = document.getElementById("dateFrom").value;
        var $dateTo = document.getElementById("dateTo").value;
        var ck_date = /^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$/;
        document.getElementById('request_button').setAttribute('disabled', 'disabled');

        var thisDate = new Date();
        var from = $dateFrom.split("-");
        var f = new Date(from[0], from[1] - 1, from[2]);
        var to = $dateTo.split("-");
        var t = new Date(to[0], to[1] - 1, to[2]);

        if (ck_date.test($dateFrom) && ck_date.test($dateTo)) {
            if (t - f >= 0) {
                if (f - thisDate >= 0) {
                    $.post("checkBeds", {dateFrom: $dateFrom, dateTo: $dateTo}, function (data) {
                        var bed_ids = JSON.parse(data);
                        // makes all cells active
                        for (var j = 1; j <= 20; j++) {
                            var clearElement = document.getElementById('t' + j);
                            clearElement.style.backgroundColor = 'rgba(255,255,255,0.9)';
                            clearElement = document.getElementById('n' + j);
                            clearElement.removeAttribute('disabled');
                        }
                        document.getElementById('date_error_set_date').style.display = 'none';
                        document.getElementById('date_error_from_to').style.display = 'none';
                        document.getElementById('date_error_from_bigger').style.display = 'none';

                        for (var i = 0; i < bed_ids.length; i++) {
                            var element = document.getElementById('n' + bed_ids[i]);
                            element.setAttribute('disabled', 'disabled');
                            document.getElementById('t' + bed_ids[i]).style.backgroundColor = "dimgrey";
                        }
                        document.getElementById('request_button').removeAttribute('disabled');
                    });
                }else{
                    document.getElementById('date_error_from_bigger').style.display = 'block';
                }
            } else{
                document.getElementById('date_error_from_to').style.display = 'block';
            }
        } else{
            document.getElementById('date_error_set_date').style.display = 'block';
        }
    });
});