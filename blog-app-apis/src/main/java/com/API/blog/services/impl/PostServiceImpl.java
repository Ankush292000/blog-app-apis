package com.API.blog.services.impl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.API.blog.entities.Category;
import com.API.blog.entities.Post;
import com.API.blog.entities.User;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.payloads.PostDto;
import com.API.blog.payloads.PostResponse;
import com.API.blog.repositories.CategoryRepo;
import com.API.blog.repositories.PostRepo;
import com.API.blog.repositories.UserRepo;
import com.API.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId , Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		Post post  = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postrepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postrepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId)) ;
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post upateedPost = this.postrepo.save(post);
		return this.modelMapper.map(upateedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postrepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postrepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {
		Sort sort= null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort= Sort.by(sortBy).ascending();
		}else {
			sort= Sort.by(sortBy).descending();
		}
		org.springframework.data.domain.Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postrepo.findAll(p);
		List <Post> allPost= pagePost.getContent();
		List<PostDto> postDtos=  allPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post SinglePost = this.postrepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		
		return this.modelMapper.map(SinglePost,PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer cstrgoryId) {
		Category cat = this.categoryRepo.findById(cstrgoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", cstrgoryId));
		List<Post> posts = this.postrepo.findByCategory(cat);
		List <PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user Id", userId));
		List<Post> posts = this.postrepo.findByUser(user);
		List <PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postrepo.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	

}
