package com.API.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
