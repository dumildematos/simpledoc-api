package com.tcc.simpledocapi.service.template;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.TemplateRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Template> listFreeTemplateByCategoryId(Long categoryId, int offset, int size) {
        return templateRepository.findFreeTemplateByCategory(categoryId, PageRequest.of(offset, size));
    }

    @Override
    public Page<Template> listAdminTemplatesByCategoryId(Long categoryId, String name, int offset, int size) {
        return categoryId != 0 ? templateRepository.findAdminTemplate(categoryId, name, PageRequest.of(offset, size)) : templateRepository.findAllAdminTemplate(name, PageRequest.of(offset, size));
    }

    @Override
    public Page<Template> listUserTemplates(Long userId, String name,  int offset, int size) {
        return templateRepository.findUserTemplate(userId, name , PageRequest.of(offset, size));
    }

    @Override
    public void deleteUserTemplateRelation(Long userId, Long tempId) {
        templateRepository.deleteUserTemplateRelation(userId, tempId);
        templateRepository.deleteById(tempId);
    }

    @Override
    public Template updateTemplate(Template template, Category category) {
        Template tmp = templateRepository.getById(template.getId());
        tmp.getCategory().clear();
        return templateRepository.save(template);
    }
}
