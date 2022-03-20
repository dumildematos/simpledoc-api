package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Document;
import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.entity.Template;
import com.tcc.simpledocapi.service.document.DocumentService;
import com.tcc.simpledocapi.service.document.form.DocumentAddForm;
import com.tcc.simpledocapi.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final TemplateService templateService;

    @PostMapping("/document/create")
    public ResponseEntity<Document> createTeamDocument(@RequestBody DocumentAddForm form){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/document/create").toUriString());

        Document document = new Document();
        document.setName(form.getName());
        if(form.getTemplateId() == null){
            document.setContent(form.getContent());
        }else {
            Optional<Template> template = templateService.getTemplate(form.getTemplateId());
            document.setContent(template.get().getContent());
        }
        document.setType(form.getType());
        document.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.created(uri).body(documentService.addDocumentToTeam(form.getTeamId(), document));
    }

    @GetMapping(value = "/team/document/list", params = {"page", "size", "teamId"})
    public ResponseEntity<Page<Team>> getUserTeams(@RequestParam int page, @RequestParam int size, @RequestParam Long teamId){
        return ResponseEntity.ok().body(documentService.getDocumentsFromTeam(page, size, teamId));
    }

}
