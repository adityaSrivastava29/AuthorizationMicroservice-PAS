package com.cognizant.auth.dto;

import java.time.LocalDateTime;

public class APIResponseError {

	  private String error;
	  private LocalDateTime dateTime;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	  
	  
	  
}
