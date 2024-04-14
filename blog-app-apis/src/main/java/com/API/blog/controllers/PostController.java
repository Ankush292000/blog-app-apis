package com.API.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.API.blog.config.AppConstants;
import com.API.blog.entities.Post;
import com.API.blog.payloads.ApiResponse;
import com.API.blog.payloads.PostDto;
import com.API.blog.payloads.PostResponse;
import com.API.blog.services.FileService;
import com.API.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image")
	private String path;
	// create 
	@PostMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost (
			@RequestBody PostDto postDto,
			
			@PathVariable Integer categoryId
			){
		
		PostDto createPost = this.postService.createPost(postDto, categoryId);
				return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
		
	}
	
	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(
			@PathVariable Integer userId
			){
		List<PostDto> posts=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get by category
		@GetMapping("/Category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostBycategory(
				@PathVariable Integer categoryId
				){
			List<PostDto> posts=this.postService.getPostByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}
		//get All post
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPost(
				@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
				@RequestParam(value = "pageSize" , defaultValue =AppConstants.PAGE_SIZE , required = false) Integer pageSize,
				@RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY ,required = false) String sortBy,
				@RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required =  false) String sortDir
				){
			PostResponse allpost = this.postService.getAllPost(pageNumber , pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(allpost , HttpStatus.OK);
		}
		
		//get by ID
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getSinglePost (
				@PathVariable Integer postId){
			PostDto singlepost = this.postService.getPostById(postId);
			
			return new ResponseEntity<PostDto>(singlepost,HttpStatus.OK);
			}
		// delete post 
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletepost (@PathVariable Integer postId) {
			this.postService.deletePost(postId);
			return new ApiResponse("Post is successfully deleted",true);
		}
		
		//update post 
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable Integer postId){
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		//search 
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity< List<PostDto>> searchPostByTitle(
				@PathVariable("keywords") String keywords
				){
			List<PostDto> result = this.postService.searchPosts(keywords);
			return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
		}
		
		@PostMapping("/posts/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId
				)throws IOException {
			PostDto postDto = this.postService.getPostById(postId);
			String fileName = this.fileService.uploadImage(path, image);
			
			postDto.setImageName(fileName);
			PostDto updatedPOst = this.postService.updatePost(postDto, postId);
		 return new ResponseEntity<PostDto>(updatedPOst,HttpStatus.OK);
		}
		
		//method to serve file
				@GetMapping(value="posts/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
				public void download (
						@PathVariable("imageName") String imageName,
						HttpServletResponse response) throws IOException{
					InputStream resource = this.fileService.getResource(path, imageName);
					response.setContentType(MediaType.IMAGE_JPEG_VALUE);
					StreamUtils.copy(resource,response.getOutputStream());
				}
}
