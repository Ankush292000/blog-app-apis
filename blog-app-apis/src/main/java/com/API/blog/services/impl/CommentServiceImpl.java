package com.API.blog.services.impl;

import java.lang.module.ResolutionException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.blog.entities.Comment;
import com.API.blog.entities.Post;
import com.API.blog.exceptions.ResourceNotFoundException;
import com.API.blog.payloads.CommentDto;
import com.API.blog.repositories.CommenctRepo;
import com.API.blog.repositories.PostRepo;
import com.API.blog.security.JwtAuthenticationFilter;
import com.API.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommenctRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JwtAuthenticationFilter jwtRequest;
	@Override
	public CommentDto createCommect(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException ("Post","post id" ,postId));
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		
		comment.setPost(post);
		comment.setUserName(jwtRequest.username);
		Comment save = this.commentRepo.save(comment);
		
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteCommect(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);
	}

	
}
