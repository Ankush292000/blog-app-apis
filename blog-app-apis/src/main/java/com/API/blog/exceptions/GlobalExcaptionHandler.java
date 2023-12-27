package com.API.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.API.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExcaptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ressourceNotFoundExceptionHandler( ResourceNotFoundException ex){
		String message =  ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);				
	}
}
