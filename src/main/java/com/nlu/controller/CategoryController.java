package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseData getCategories() {
		return ResponseData.builder()
				.status(200)
				.message("Data categories")
				.data(categoryService.getAllCategories())
				.build();
	}
}
