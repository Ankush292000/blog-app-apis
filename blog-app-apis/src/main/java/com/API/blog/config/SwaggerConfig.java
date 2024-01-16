package com.API.blog.config;


import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@Configuration
@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Ankush",
						email = "ankushboraste29@gmail.com",
						url = "9552795235"
						
						) ,
				description = "This  are rest APIs in spring boot. In this we can perfrom multiple oprations like create user add post (add update delete get all get single) etc for blogging "
						+"\n"+ " 1st you have to create user then using that user credentials create token using it in Authentication  ",
				title = "Blogging APIs",
				version = "1.0",
				
				termsOfService = "terms and condition"
				),
		security = {
				@SecurityRequirement(name ="bearerAuth")
		}
	
		)
@SecurityScheme(
		name = "bearerAuth",
		description = "Enter JWT Token (bearer_Token)",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
public class SwaggerConfig {

	
}
