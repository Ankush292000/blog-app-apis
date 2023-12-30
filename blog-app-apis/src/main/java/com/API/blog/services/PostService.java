package com.API.blog.services;

import java.util.List;

import com.API.blog.entities.Post;
import com.API.blog.payloads.PostDto;

public interface PostService {
	
	//create
	
	PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);
	
	//update 
	
	Post updatePost(PostDto postDto, Integer postId);
	
	//delete
	
	void deletePost (Integer postId);
	
	//get All post
	
	List<Post> getAllPost();
	
	//get  single Post
	
	Post getPostById(Integer postId);
	
	// get all post by category 
	
	List<Post> getPostByCategory( Integer vstrgoryId);
	
	// get all posts by user
	
	List <Post> getPostByUser(Integer userId);
	
	//search posts
	
	List<Post> searchPosts (String keyword);

}
