package com.API.blog.services;

import com.API.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createCommect(CommentDto commentDto , Integer postId);
	
	void deleteCommect(Integer commentId);
}
