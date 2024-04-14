package com.API.blog.security;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.API.blog.entities.User;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.repositories.UserRepo;

@Service
public class CustomUserDetails implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load user from database by username
		try {
            // Load user from the database by username
            User user = this.userRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

            return user;
        } catch (UsernameNotFoundException ex) {
            // Handle the exception and return a UserDetails with an empty set of authorities
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password("dummyPassword") // Provide a dummy password to avoid NPE
                    .authorities(new HashSet<>()) // Empty set of authorities
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
    }
	

}
