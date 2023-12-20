package com.API.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String fieldName;
	long field;
	public ResourceNotFoundException(String resourceName, String fieldName, long field) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,field));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.field = field;
	}
	

}
