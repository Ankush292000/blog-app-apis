package com.API.blog.services.impl;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.blog.entities.User;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.payloads.UserDto;
import com.API.blog.repositories.UserRepo;
import com.API.blog.services.UserService;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class UserSeriveImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user =this.dtoToUser(userDto);
		User savedUser= this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
	

		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		User updatedUser = this.userRepo.save(user);
		UserDto userSto1= this.userToDto(updatedUser);
		return userSto1;
	}

	@Override
	public UserDto getUserByID(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List <UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto userDto) {
		User user =new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		return user;
	}
	public UserDto userToDto (User user) {
		UserDto userDto = new UserDto ();
		userDto.setId(user.getId());
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		return userDto;		
	}

}
