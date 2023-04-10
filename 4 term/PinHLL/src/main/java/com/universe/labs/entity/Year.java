package com.universe.labs.entity;

import org.springframework.http.HttpStatusCode;

public class Year{

	private int NumberOfYear;
	
	private Boolean Leap;
	private int Days;

	private String Message;
	private HttpStatusCode httpStatusCode;
	
	
	public Year() {NumberOfYear = 0;}
	public Year(int Number) {this.NumberOfYear = Number;}
	public Year(Year year) {this.NumberOfYear = year.NumberOfYear;}
	
	public int getNumber() {
		return NumberOfYear;
	}
	public void setNumber(int number) {
		NumberOfYear = number;
	}
	public Boolean getLeap() {
		return Leap;
	}
	public void setLeap(Boolean leap) {
		Leap = leap;
	}
	public int getDays() {
		return Days;
	}
	public void setDays(int days) {
		Days = days;
	}
	public String getMessage()
	{
		return Message;
	}
	public void setErrorMessage(String Messaage) {
		Message = Messaage;
	}
	public HttpStatusCode getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	
	
}
