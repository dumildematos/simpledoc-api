package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.Contributor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ContributorService {

    Contributor addDocumentContributor(Long documentId, Contributor contributor);
    Contributor findContributor(String username, Long docId);
    void deleteContributor(Long contrId, Long docId);
    Collection<Contributor> listDocumentContributors(Long docId);
}
