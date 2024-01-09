package com.API.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.blog.entities.Role;
import com.API.blog.entities.User;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.payloads.ApiResponse;
import com.API.blog.payloads.UserDto;
import com.API.blog.repositories.RoleRepo;
import com.API.blog.security.JwtAuthenticationFilter;
import com.API.blog.services.UserService;
import com.API.blog.utilts.JWTRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtAuthenticationFilter jwtRequest;
	@Autowired
	private RoleRepo rolerepo;
	//Post -create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	// PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable Integer userId){
		UserDto updatedUsser = this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updatedUsser);
		
	}
	
	// Delete user

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser (@PathVariable Integer userId) {
		UserDto gettingUser = this.userService.getUserByID(userId);
		if (gettingUser.getEmail().equals(jwtRequest.getUsername())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Can not delete existing user" , false) , HttpStatus.OK);
		}
		
		
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully " , true) , HttpStatus.OK);
	}
	//GET -user get 
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	//GET - user get 
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserByID(userId));
	}
	
}
