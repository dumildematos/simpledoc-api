package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.config.websocket.WebsocketNotification;
import com.tcc.simpledocapi.entity.*;
import com.tcc.simpledocapi.service.contributor.ContributorService;
import com.tcc.simpledocapi.service.contributor.form.AddContributorToDocForm;
import com.tcc.simpledocapi.service.document.DocumentService;
import com.tcc.simpledocapi.service.document.form.DocumentAddForm;
import com.tcc.simpledocapi.service.template.TemplateService;
import com.tcc.simpledocapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;
    private final TemplateService templateService;
    private final UserService userService;
    private final ContributorService contributorService;


    @PostMapping("/document/create")
    public ResponseEntity<Document> createTeamDocument(@RequestBody DocumentAddForm form, Principal principal){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/document/create").toUriString());

        Document document = new Document();
        document.setName(form.getName());
        document.setCreator(principal.getName());
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
    public ResponseEntity<Page<Document>> getUserTeams(@RequestParam int page, @RequestParam int size, @RequestParam Long teamId){
        return ResponseEntity.ok().body(documentService.getDocumentsFromTeam(page, size, teamId));
    }

    @PostMapping("/document/add/contributor")
    public ResponseEntity<?> addContributorToDocumento(@RequestBody AddContributorToDocForm form, Principal principal){

            Optional<User> user = Optional.ofNullable(userService.getUser(form.getUsername()));

            Contributor contributor;

            if(!user.isPresent())
                throw new IllegalArgumentException("User doesnt exist");
            else {

                Optional<Contributor> isContributor = Optional.ofNullable(contributorService.findContributor(user.get().getUsername(), form.getDocumentId()));

                if(isContributor.isPresent() || principal.getName().equals(form.getUsername())){
                    throw new IllegalArgumentException("Contributor can not be added");
                }else {
                    contributor = new Contributor(null,
                            user.get().getUsername(),
                            user.get().getFirstname(),
                            user.get().getLastname(),
                            form.getTeamId(),
                            form.getDocumentId(),
                            user.get().getAvatar(),
                            form.getRole());
                }

            }
        return ResponseEntity.ok().body(contributorService.addDocumentContributor(form.getDocumentId(), contributor));
    }

    @DeleteMapping(value = "/document/{docId}/{teamId}")
    public ResponseEntity<Document> deleteDocument(@PathVariable Long docId, @PathVariable Long teamId){

        documentService.deleteDocument(docId, teamId);
        return ResponseEntity.ok().build();
    }

    /*@MessageMapping("/message")
    @SendTo("/chatroom/public")
    public WebsocketNotification receivePublicMessage(@Payload WebsocketNotification message) {
        return message;
    }*/



}

