package com.epam.hostel.validation;

public enum ValidationResult {
	ALL_RIGHT,
	EMAIL_INCORRECT,
    PASS_INCORRECT,
    LNAME_INCORRECT,
    FNAME_INCORRECT,    
    PHONE_INCORRECT,
    DATE_INCORRECT,
    DATA_INCORRECT,
    NO_ID,
    NO_USER,
    NO_DATES,
    NO_BEDS,
    BEDS_NOT_FREE,
    NO_FIELDS,
       
    PHONE_NOT_UNIQUE,
    EMAIL_NOT_UNIQUE,    
    
    NOT_ALL_FIELDS,
    UNKNOWN_ERROR;
}
