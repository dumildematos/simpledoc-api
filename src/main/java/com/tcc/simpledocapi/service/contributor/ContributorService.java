package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.Contributor;
import org.springframework.stereotype.Service;

@Service
public interface ContributorService {
    Contributor createContributor(Contributor contributor);
    Contributor addDocumentContributtor(Long documentId, Contributor contributor);
}
