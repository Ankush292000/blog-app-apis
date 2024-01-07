package com.API.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.blog.entities.Comment;

public interface CommenctRepo extends JpaRepository<Comment, Integer>{

}
