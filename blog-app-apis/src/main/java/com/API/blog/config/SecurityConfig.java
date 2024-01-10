package com.API.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.API.blog.security.JwtAuthenticationEntryPoint;
import com.API.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc

public class SecurityConfig {
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }
	    
	    @Autowired
	    private JwtAuthenticationEntryPoint point;
	    @Autowired
	    private JwtAuthenticationFilter filter;
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	http.csrf(csrf -> csrf.disable()).
	    	cors(cors -> cors.disable())
	    	.authorizeHttpRequests(auth -> 
	    	auth.requestMatchers("/home/**").
	    	authenticated().requestMatchers("/auth/**").permitAll().
	    	requestMatchers("/api/users/").permitAll().
	    	requestMatchers("/v2/api-docs/", "/v3/api-docs/","/swagger-ui/", "/swagger-resources/", "/swagger.ui.html").permitAll().
	    	requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
	    	.requestMatchers(HttpMethod.GET).permitAll()
	    	.anyRequest().authenticated())
	    	.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
	    	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    	http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
	    	
	    }
	    
	    boolean prePostEnabled() { return true;};
}
