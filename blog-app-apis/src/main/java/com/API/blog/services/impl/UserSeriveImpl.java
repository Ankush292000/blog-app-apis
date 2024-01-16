package com.API.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.API.blog.config.AppConstants;
import com.API.blog.entities.Role;
import com.API.blog.entities.User;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.payloads.UserDto;
import com.API.blog.repositories.RoleRepo;
import com.API.blog.repositories.UserRepo;
import com.API.blog.services.UserService;



@Service
public class UserSeriveImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
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
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setUserType(userDto.getUserType());
		Role role =null;
		try {
			String Admin = userDto.getUserType();
			if(Admin.equalsIgnoreCase("Admin")) {
				role =this.roleRepo.findById(AppConstants.NORMAL_ADMIN).get();
			}else {
				role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			}
		}catch (NullPointerException e) {
			// TODO: handle exception
			role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		}
		user.getRoles().add(role);
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
		User user = this.modelMapper.map(userDto, User.class);
		/*
		 * User user =new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setAbout(userDto.getAbout());
		 * user.setPassword(userDto.getPassword()); user.setEmail(userDto.getEmail());
		 */
		return user;
	}
	public UserDto userToDto (User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class); 
		/*
		 * UserDto userDto = new UserDto (); userDto.setId(user.getId());
		 * userDto.setAbout(user.getAbout()); userDto.setEmail(user.getEmail());
		 * userDto.setName(user.getName()); userDto.setPassword(user.getPassword());
		 */
		return userDto;		
	}

	@Override
	public UserDto registerNewUser(UserDto user) {
		User users = this.modelMapper.map(user, User.class);
		//encode password
		
		users.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role =null;
		try {
			String Admin = users.getUserType();
			if(Admin.equalsIgnoreCase("Admin")) {
				role =this.roleRepo.findById(AppConstants.NORMAL_ADMIN).get();
			}else {
				role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			}
		}catch (NullPointerException e) {
			// TODO: handle exception
			role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		}
		users.getRoles().add(role);
		User newuser = this.userRepo.save(users);
		
		return this.modelMapper.map(newuser, UserDto.class);
	}

}
