package com.API.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.blog.entities.Category;
import com.API.blog.entities.Post;
import com.API.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	List <Post> getAByUser(User user);
	
	List <Post> findByCategory(Category category);
}
