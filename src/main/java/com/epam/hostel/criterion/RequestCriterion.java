package com.epam.hostel.criterion;

public enum RequestCriterion {
	ID("id"),
	USER_ID("user_id"),
	ADMINISTRATOR_ID("administrator_id"),
	STATUS_ID("status_id"),
		
	CREATION_DATE("creation_date"),
	DATE_FROM("date_from"),
	DATE_TO("date_to"),
	SEAT_COUNT("seat_count"),	
	COMMENT("comment"),
	REGISTRATION_DATE("registration_date"),
	LAST_MODIFIED_DATE("last_modified_date"),
	PRICE("price");
	
	private String SQLFieldName;
	
	private RequestCriterion(String SQLFieldName) {
		this.SQLFieldName = SQLFieldName;
	}

	public String getSQLFieldName() {
		return SQLFieldName;
	}	

}
