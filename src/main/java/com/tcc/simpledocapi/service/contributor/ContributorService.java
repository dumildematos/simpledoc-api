package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.Contributor;
import org.springframework.stereotype.Service;

@Service
public interface ContributorService {

    Contributor addDocumentContributor(Long documentId, Contributor contributor);
    Contributor findContributorByUsername(String username);
    void deleteContributor(Long contrId, Long docId);
}
