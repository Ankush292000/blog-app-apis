package com.API.blog.services;

import java.util.List;

import com.API.blog.entities.Post;
import com.API.blog.payloads.PostDto;
import com.API.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	
	PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);
	
	//update 
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	
	void deletePost (Integer postId);
	
	//get All post
	
	PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir);
	
	//get  single Post
	
	PostDto getPostById(Integer postId);
	
	// get all post by category 
	
	List<PostDto> getPostByCategory( Integer cstrgoryId);
	
	// get all posts by user
	
	List <PostDto> getPostByUser(Integer userId);
	
	//search posts
	
	List<PostDto> searchPosts (String keyword);

}
