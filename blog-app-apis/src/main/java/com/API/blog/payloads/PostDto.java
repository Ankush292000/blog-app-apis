package com.API.blog.payloads;

import java.sql.Date;

import com.API.blog.entities.Category;
import com.API.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private String title;
	
	private String content;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private String imageName;
	
}
