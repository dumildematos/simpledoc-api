package com.tcc.simpledocapi.service.document;

import com.tcc.simpledocapi.entity.Contributor;
import com.tcc.simpledocapi.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DocumentService {
    Document addDocumentToTeam(Long teamId , Document document);
    Page<Document> getDocumentsFromTeam(int offset, int size, Long teamId);
    void deleteDocument(Long documentId, Long teamId);

    Optional<Document> getDocumentById(Long id);

    Long userTotalOfDocuments(Long userId);
}
