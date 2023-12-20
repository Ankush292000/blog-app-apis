package com.API.blog.services;

import java.util.List;

import com.API.blog.entities.User;
import com.API.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser (UserDto user);
	
	UserDto updateUser (UserDto user , Integer userId);
	
	UserDto getUserByID(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
