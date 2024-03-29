package com.API.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.API.blog.entities.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {
	@Schema(hidden = true)
	private int id;
	
	@NotEmpty
	@Size(min =4 ,message = "username must be 4 characters")
	
	private String name;
	
	@Email
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotNull
	private String about;
	
	private String userType;
	@Schema(hidden = true)
	private Set<RoleDto> roles = new HashSet<>();
}
