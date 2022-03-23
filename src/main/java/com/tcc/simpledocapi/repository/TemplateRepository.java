package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query(value = "select distinct * from template t inner join template_category tc on  t.id = tc.template_id and t.price = '0.00' and tc.category_id = ?1", nativeQuery = true)
    Page<Template> findFreeTemplateByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

}
