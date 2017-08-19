package com.epam.hostel.criterion;

public enum AdministratorCriterion {
	ID("id"),	
	PHONE("phone"),
	PASSWORD("password"),
	FIRST_NAME("first_name"),
	LAST_NAME("last_name"),	
	BIRTH_DATE("birth_date");
	
	private String SQLFieldName;
	
	private AdministratorCriterion(String SQLFieldName) {
		this.SQLFieldName = SQLFieldName;
	}

	public String getSQLFieldName() {
		return SQLFieldName;
	}	
}
