package com.API.blog.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.API.blog.payloads.ApiResponse;

@RestControllerAdvice //annotation for exception handling 
public class GlobalExcaptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ressourceNotFoundExceptionHandler( ResourceNotFoundException ex){
		String message =  ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);				
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> MethodArgumentNotValidExceptionHandler( MethodArgumentNotValidException ex){
		Map<String,String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)-> {
			String filedName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(filedName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);		
	}
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse> NoResourceFoundExceptionHandler( NoResourceFoundException ex){
		String message =  ex.getMessage() + "(Tip -: check url)";
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);				
	}
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> SQLIntegrityConstraintViolationExceptionExceptionHandler( SQLIntegrityConstraintViolationException ex){
		String message =  ex.getMessage() + "(Tip -: check url)";
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);				
	}
	}

