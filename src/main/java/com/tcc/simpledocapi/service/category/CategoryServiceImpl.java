package com.tcc.simpledocapi.service.category;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<Category> listCategory(int offset, int size) {
        return categoryRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public Category deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent())
            throw new IllegalArgumentException("");
        categoryRepository.deleteById(id);
        return category.get();
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }


}
