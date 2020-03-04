package com.irrigation.waterordering.error;


import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse generationExceptionHandler(Exception e){
	  
	    return new ErrorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*@ExceptionHandler(WaterOrderNotFoundException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.NOT_FOUND.value());
	}*/
	
	 @ExceptionHandler(WaterOrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

		 ErrorResponse errors = new ErrorResponse();
         errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
	 
	 @ExceptionHandler(WaterOrderException.class)
	    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, WebRequest request) {

			 ErrorResponse errors = new ErrorResponse();
	        errors.setError(ex.getMessage());
	        errors.setStatus(HttpStatus.BAD_REQUEST);
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		 Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());

	        //Get all errors
	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);

	        return new ResponseEntity<>(body, headers, status);
		
	}

}
