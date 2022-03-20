package com.tcc.simpledocapi.service.category;

import com.tcc.simpledocapi.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Optional<Category> findCategoryById(Long id);
}
