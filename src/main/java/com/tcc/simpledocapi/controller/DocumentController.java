package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Document;
import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.repository.TeamRepository;
import com.tcc.simpledocapi.service.document.DocumentService;
import com.tcc.simpledocapi.service.document.form.DocumentAddForm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final TeamRepository teamRepository;

    @PostMapping("/document/create")
    public ResponseEntity<Document> createTeamDocument(@RequestBody DocumentAddForm form){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/document/create").toUriString());

        Document document = new Document();
        document.setName(form.getName());
        document.setContent(form.getContent());
        document.setType(form.getType());
        document.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.created(uri).body(documentService.addDocumentToTeam(form.getTeamId(), document));
    }

    @GetMapping(value = "/team/document/list", params = {"page", "size", "teamId"})
    public ResponseEntity<Page<Team>> getUserTeams(@RequestParam int page, @RequestParam int size, @RequestParam Long teamId){
        return ResponseEntity.ok().body(documentService.getDocumentsFromTeam(page, size, teamId));
    }

}
