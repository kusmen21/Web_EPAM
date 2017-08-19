var ck_name = /^[A-Za-zА-Яа-я]{3,}$/;
var ck_password = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
//var ck_age = /(^[7-9]$)|(^[0-9]{2}$)|(^[0,1][0-1][0-9]$)|(^120$)/;
var ck_email = /.+@.+\./;
var ck_phone = /^[0-9]{7,20}$/;

function showError(errorCode) {
    /*

    */
    switch (errorCode) {
        case 1: document.getElementById('email_error_set_email').style.display = 'block';
            break;
        case 2: document.getElementById('email_error_wrong_email').style.display = 'block';
            break;
        case 11: document.getElementById('password_error_set_password').style.display = 'block';
            break;
        case 13: document.getElementById('password_error_wrong_password').style.display = 'block';
            break;
        case 21: document.getElementById('password2_error_set_password').style.display = 'block';
            break;
        case 22: document.getElementById('password2_error_not_equals').style.display = 'block';
            break;
        case 31: document.getElementById('phone_error_set_phone').style.display = 'block';
            break;
        case 32: document.getElementById('phone_error_wrong_phone').style.display = 'block';
            break;
        case 41: document.getElementById('name_error_set_name').style.display = 'block';
            break;
        case 42: document.getElementById('name_error_wrong_name').style.display = 'block';
            break;
        case 51: document.getElementById('surname_error_set_surname').style.display = 'block';
            break;
        case 52: document.getElementById('surname_error_wrong_surname').style.display = 'block';
            break;
    }
}

function resetError() {
    var errorElements = document.getElementsByClassName("error_message");
    for (var i = 0; i < errorElements.length; i++) {
        var element = errorElements[i];
        element.style.display = 'none';
    }
}

function validate(form) {
    var elements = form.elements;
    var hasErrors = false;
    resetError();

    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];

        if (element.name == "email") {
            if (!element.value) {
                showError(1);
                hasErrors = true;
            } else if (!ck_email.test(element.value)) {
                showError(2);
                hasErrors = true;
            }
        }

        if (!elements.password.value) {
            showError(11);
            hasErrors = true;
        }  else if (!ck_password.test(elements.password.value)) {
            showError(13);
            hasErrors = true;
        }

        if (!elements.password2.value) {
            showError(21);
            hasErrors = true;
        } else if (elements.password.value != elements.password2.value) {
            showError(22);
            hasErrors = true;
        }

        if (!elements.phone.value) {
            showError(31);
            hasErrors = true;
        }  else if (!ck_phone.test(elements.phone.value)) {
            showError(32);
            hasErrors = true;
        }

        if (!elements.fname.value) {
            showError(41);
            hasErrors = true;
        }  else if (!ck_name.test(elements.fname.value)) {
            showError(42);
            hasErrors = true;
        }

        if (!elements.lname.value) {
            showError(51);
            hasErrors = true;
        }  else if (!ck_name.test(elements.lname.value)) {
            showError(52);
            hasErrors = true;
        }

        if (hasErrors == false){
            document.getElementById('registration_button').removeAttribute('disabled');
        } else{
            document.getElementById('registration_button').setAttribute('disabled', 'disabled');
        }
    }
}
