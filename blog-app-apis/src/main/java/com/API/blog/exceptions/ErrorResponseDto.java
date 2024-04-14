package com.API.blog.exceptions;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private String errorMessage;
	 public ErrorResponseDto(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }
}
