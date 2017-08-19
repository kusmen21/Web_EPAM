package com.epam.hostel.criterion;


public enum UserCriterion {
	ID("id"),
	EMAIL("email"),
	PHONE("phone"),
	PASSWORD("password"),
	FIRST_NAME("first_name"),
	LAST_NAME("last_name"),
	IS_BANNED("is_banned"),
	DISCOUNT("discount"),
	REGISTRATION_DATE("registration_date"),
	BIRTH_DATE("birth_date");
	
	private String SQLFieldName;
	
	private UserCriterion(String SQLFieldName) {
		this.SQLFieldName = SQLFieldName;
	}

	public String getSQLFieldName() {
		return SQLFieldName;
	}	
	
}
