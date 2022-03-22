package com.tcc.simpledocapi.service.category;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Optional<Category> findCategoryById(Long id);
    Page<Category> listCategory(int offset, int size);
}
