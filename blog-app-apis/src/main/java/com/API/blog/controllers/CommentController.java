package com.API.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.blog.entities.Comment;
import com.API.blog.payloads.ApiResponse;
import com.API.blog.payloads.CommentDto;
import com.API.blog.security.JwtAuthenticationFilter;
import com.API.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private JwtAuthenticationFilter jwtRequest;

	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment ,
			@PathVariable Integer postId
			){
		CommentDto createCommect =this.commentService.createCommect(comment, postId);
		return new ResponseEntity<CommentDto>(createCommect,HttpStatus.CREATED);
	}
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteCommect(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted!!!!",true),HttpStatus.OK);
	}
	@PutMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> updateComment(@RequestBody CommentDto comment,@PathVariable Integer commentId){
		
		CommentDto createCommect = this.commentService.UpdateCommect(comment,commentId);
		if(createCommect.getUserName() != null){
			return new ResponseEntity<ApiResponse>(new ApiResponse("comment updated",true),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment not updated !!!",false),HttpStatus.OK);
	}
}
