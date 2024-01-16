package com.API.blog.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
	@Schema(hidden = true)
	private int id;
	
	private String content;
	@Schema(hidden = true)
	private String UserName;
	
}
