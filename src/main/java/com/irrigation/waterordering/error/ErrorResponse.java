package com.irrigation.waterordering.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
  private String error;
  private HttpStatus status;

public String getError() {
	return error;
}

public void setError(String error) {
	this.error = error;
}





public HttpStatus getStatus() {
	return status;
}

public void setStatus(HttpStatus status) {
	this.status = status;
}



public ErrorResponse(String error, HttpStatus status) {
	super();
	this.error = error;
	this.status = status;
}

public ErrorResponse() {
	
}
  
  
}
