package com.tcc.simpledocapi.service.template;

import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService{

    private final TemplateRepository templateRepository;

    @Override
    public Optional<Template> getTemplate(Long id) {
        return templateRepository.findById(id);
    }
}
