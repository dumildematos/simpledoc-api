package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Contributor;
import com.tcc.simpledocapi.service.contributor.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;

    @DeleteMapping(value= "/contributor/{contrId}/{docId}")
    public ResponseEntity<Contributor> deleteContributor(@PathVariable Long contrId , @PathVariable Long docId){
        contributorService.deleteContributor(contrId, docId);
        return ResponseEntity.ok().build();
    }


}
