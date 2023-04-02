package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.Contributor;
import com.tcc.simpledocapi.entity.User;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Optional;

@Service
public interface ContributorService {

    Contributor addDocumentContributor(Long documentId, Contributor contributor, User owner) throws MalformedURLException;
    Contributor findContributor(String username, Long docId);
    Optional<Contributor> findById(Long id);
    void deleteContributor(Long contrId, Long docId);

    void deteleContributorByDocumentTeamUserId(Long documentId, Long teamId, Long userId);
    void save(Contributor contributor);
    Collection<Contributor> listDocumentContributors(Long docId);
}
