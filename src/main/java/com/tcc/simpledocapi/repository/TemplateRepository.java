package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
