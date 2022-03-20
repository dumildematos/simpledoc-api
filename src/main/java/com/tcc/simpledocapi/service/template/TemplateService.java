package com.tcc.simpledocapi.service.template;

import com.tcc.simpledocapi.entity.Template;

import java.util.Optional;

public interface TemplateService {
    Optional<Template> getTemplate(Long id);
    Template createTemplate(String username, Template template);
}
