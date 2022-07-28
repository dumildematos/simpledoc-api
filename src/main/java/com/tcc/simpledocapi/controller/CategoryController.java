package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/category/list", params = {"page","size"})
    public ResponseEntity<Page<Category>> listCategory(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(categoryService.listCategory(page, size));
    }

    @DeleteMapping (value ="/category/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.deleteCategory(id));
    }

    @PutMapping(value="/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if(!id.equals(category.getId()))
            throw new IllegalArgumentException("Operação inválida");
        return ResponseEntity.ok().body(categoryService.updateCategory(category));
    }

}
