package com.API.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.blog.payloads.ApiResponse;
import com.API.blog.payloads.CategoryDto;
import com.API.blog.services.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")

public class categoryControllers {
	
	@Autowired
	private CategoryService categoryService;
	
	//create 
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCatgory (@Valid @RequestBody CategoryDto cateogDto){
		CategoryDto createCategory = this.categoryService.createCategory(cateogDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	//update 
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCatgory (@Valid @RequestBody CategoryDto cateogDto , @PathVariable Integer catId){
		CategoryDto updateCategory = this.categoryService.updateCategory(cateogDto , catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.CREATED);
	}
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCatgory ( @PathVariable Integer catId){
		 this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully !!", true),HttpStatus.OK);
	}
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCatgory (@PathVariable Integer catId){
		CategoryDto SingleCategory = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(SingleCategory,HttpStatus.OK);
	}
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCatgory (){
		List<CategoryDto> AllCategory = this.categoryService.getAllCategories();
		return ResponseEntity.ok(AllCategory);
	}
}
