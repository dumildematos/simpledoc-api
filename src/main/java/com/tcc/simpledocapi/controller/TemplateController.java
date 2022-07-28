package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Category;
import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.service.category.CategoryService;
import com.tcc.simpledocapi.service.template.TemplateService;
import com.tcc.simpledocapi.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/template/create")
    public ResponseEntity<Template> createTemplate(@RequestBody CreateTemplateForm form, Principal principal) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/template/create").toUriString());
        Optional<Category> category = form.getCategoryId() != null ?  categoryService.findCategoryById(form.getCategoryId()) : null;
        Template temp = templateService.createTemplate(principal.getName(),
                new Template(null,
                        form.getName(),
                        form.getDescription(),
                        form.getContent(),
                        form.getCover() ,
                        LocalDateTime.now() ,
                        form.getPrice() ,
                        form.getCategoryId() != null ? Arrays.asList(category.get()) : null )
        );
        User user = userService.getUser(principal.getName());
        user.getTemplates().add(temp);

        return ResponseEntity.created(uri).body(temp);
    }

    @GetMapping(value = "/template/marketplace/free", params = {"page","size","categoryId"})
    public ResponseEntity<Page<Template>> listFreeTemplateByCategory(@RequestParam int page, @RequestParam int size, @RequestParam Long categoryId) {
        return ResponseEntity.ok().body(templateService.listFreeTemplateByCategoryId(categoryId, page, size));
    }

    @GetMapping(value = "/template/me/list", params = {"page","size","name"})
    public ResponseEntity<Page<Template>> listUserTemplates(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> name, Principal principal) {
        User user = userService.getUser(principal.getName());
        return ResponseEntity.ok().body(templateService.listUserTemplates(user.getId(), name.orElse("_") , page, size));
    }

    @GetMapping(value = "/template/marketplace", params = {"page","size","categoryId", "name"})
    public ResponseEntity<Page<Template>> listMarketPlaceTemplatesByCategory(@RequestParam int page, @RequestParam int size, @RequestParam Long categoryId, @RequestParam Optional<String> name) {
        return ResponseEntity.ok().body(templateService.listAdminTemplatesByCategoryId(categoryId, name.orElse("_"), page, size));
    }

    @DeleteMapping("/template/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable("id") Long id, Principal principal){
        User user = userService.getUser(principal.getName());
        templateService.deleteUserTemplateRelation(user.getId(), id);
        return ResponseEntity.ok().build();
    };

    @PutMapping("/template/{id}")
    public ResponseEntity<Template> updateTemplate(@PathVariable("id") Long id ,@RequestBody CreateTemplateForm form) {
        Optional<Template> oldTemp = templateService.getTemplate(id);
        Optional<Category> category = categoryService.findCategoryById(form.getCategoryId());
        // Optional<Category> category = form.getCategoryId() != null ? categoryService.findCategoryById(form.getCategoryId()) : null;
        if(!oldTemp.isPresent() || !category.isPresent())
            throw new IllegalArgumentException("Template não existe");

        oldTemp.get().getCategory().remove(category.get());
        Template template = new Template(id,
                form.getName(),
                form.getDescription(),
                form.getContent(),
                form.getCover(),
                LocalDateTime.now() ,
                form.getPrice(),
                new ArrayList<>());
        template.getCategory().add(category.get());
        return  ResponseEntity.ok().body(templateService.updateTemplate(template, category.get()));
    }

}

@Data
class CreateTemplateForm {
    private String name;
    private String description;
    private String content;
    private String price;
    private String cover;
    private Long categoryId;
}