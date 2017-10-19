package com.epam.hostel.criterion;


public enum BedCriterion {
	ID("id"),	
	PRICE("price");
	
	private String SQLFieldName;
	
	private BedCriterion(String SQLFieldName) {
		this.SQLFieldName = SQLFieldName;
	}

	public String getSQLFieldName() {
		return SQLFieldName;
	}	
}
