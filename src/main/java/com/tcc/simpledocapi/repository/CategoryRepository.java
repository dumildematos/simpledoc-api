package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
