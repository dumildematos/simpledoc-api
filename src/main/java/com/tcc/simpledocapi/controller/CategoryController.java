package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/category/create").toUriString());

        return ResponseEntity.created(uri).body(categoryService.createCategory(category));
    }

}
