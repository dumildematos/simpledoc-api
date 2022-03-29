package com.tcc.simpledocapi.service.document;

import com.tcc.simpledocapi.entity.*;
import com.tcc.simpledocapi.repository.DocumentRepository;
import com.tcc.simpledocapi.repository.InvitedTeamRepo;
import com.tcc.simpledocapi.repository.TeamRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentImpl implements DocumentService{

    private final DocumentRepository documentRepository;
    private final TeamRepository teamRepository;

    @Override
    public Document addDocumentToTeam(Long teamId , Document document) {
         Document doc = documentRepository.save(document);
         Optional<Team> teams = teamRepository.findById(teamId);
         teams.map(team -> team.getDocuments().add(doc));
         return doc;
    }

    @Override
    public Page<Document> getDocumentsFromTeam(int offset, int size, Long teamId) {
        return documentRepository.findDocumentsByTeam(teamId, PageRequest.of(offset, size));
    }

}
