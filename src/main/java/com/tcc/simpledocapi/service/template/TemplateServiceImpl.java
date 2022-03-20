package com.tcc.simpledocapi.service.template;

import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.TemplateRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService{

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Template> getTemplate(Long id) {
        return templateRepository.findById(id);
    }

    @Override
    public Template createTemplate(String username, Template template) {
        Template temp = templateRepository.save(template);
        User user = userRepository.findByUsername(username);
        user.getTemplates().add(temp);
        return temp;
    }
}
