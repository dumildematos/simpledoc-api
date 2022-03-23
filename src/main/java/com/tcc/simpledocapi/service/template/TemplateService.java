package com.tcc.simpledocapi.service.template;

import com.tcc.simpledocapi.entity.Template;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TemplateService {
    Optional<Template> getTemplate(Long id);
    Template createTemplate(String username, Template template);
    Page<Template> listFreeTemplateByCategoryId(Long categoryId, int offset , int size);
}
