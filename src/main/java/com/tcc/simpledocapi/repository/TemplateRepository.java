package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query(value = "SELECT DISTINCT id from template t inner join template_category tc on  tc.category_id = ?1", nativeQuery = true)
    Page<Template> findTemplateByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

}
