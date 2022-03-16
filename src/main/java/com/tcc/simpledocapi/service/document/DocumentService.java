package com.tcc.simpledocapi.service.document;

import com.tcc.simpledocapi.entity.Document;
import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DocumentService {
    Document addDocumentToTeam(Long teamId , Document document);
    Page<Team> getDocumentsFromTeam(int offset, int size, Long teamId);
}
