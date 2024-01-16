package com.API.blog.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoleDto {
	@Schema(hidden = true)
	private int id;
	
	private String name;
}
