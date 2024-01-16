package com.API.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.API.blog.entities.Category;
import com.API.blog.entities.Comment;
import com.API.blog.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	@Schema(hidden = true)
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private Date addedDate;
	@Schema(hidden = true)
	private CategoryDto category;
	@Schema(hidden = true)
	private UserDto user;
	
	private String imageName;
	
	private Set<CommentDto> comments = new HashSet<>();
	
}
