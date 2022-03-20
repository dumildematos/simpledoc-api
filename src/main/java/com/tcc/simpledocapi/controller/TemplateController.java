package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.service.category.CategoryService;
import com.tcc.simpledocapi.service.template.TemplateService;
import com.tcc.simpledocapi.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/template/create")
    public ResponseEntity<Template> createTemplate(@RequestBody CreateTemplateForm form) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/template/create").toUriString());

        Optional<Category> category = form.getCategoryId() != null ?  categoryService.findCategoryById(form.getCategoryId()) : null;
        Template temp = templateService.createTemplate(form.getUsername(),
                new Template(null, form.getName(), form.getContent() , LocalDateTime.now() , form.getPrice() , form.getCategoryId() != null ? category.get() : null )
        );
        User user = userService.getUser(form.getUsername());
        user.getTemplates().add(temp);

        return ResponseEntity.created(uri).body(temp);
    }

}

@Data
class CreateTemplateForm {
    private String name;
    private String content;
    private String price;
    private Long categoryId;
    private String username;
}