package com.example.demo.dao;

public enum BoxStatus {
	FREE("table-success"), 
	DEPOSIT(""), 
	BOOKED("table-danger");
	
	private String css;
	
	BoxStatus(String css) {
		this.css=css;
	}
	
	public String getCss() {
		return css;
	}
}
