package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.*;
import com.tcc.simpledocapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContributorImpl implements ContributorService{

    private final ContributorRepository contributorRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public Contributor addDocumentContributor(Long documentId, Contributor contributor) {

        Optional<Document> document = documentRepository.findById(documentId);

        User user = userRepository.findByUsername(contributor.getUsername());
        Optional<Team> team = teamRepository.findById(contributor.getTeamId());
        team.ifPresent(value -> user.getInvitedTeams().add(value));

        Contributor cont = contributorRepository.save(contributor);
        document.ifPresent(value -> value.getContributors().add(cont));

        return  cont;
    }

    @Override
    public Contributor findContributor(String username, Long docId) {
        return contributorRepository.findContributor(username, docId);
    }

    @Override
    public void deleteContributor(Long contrId, Long docId) {
        documentRepository.deleteDocumentContributorRelation(docId, contrId);
        contributorRepository.deleteContributorFromDocument(contrId, docId);
        teamRepository.findById(docId);
    }

    @Override
    public Collection<Contributor> listDocumentContributors(Long docId) {
        return contributorRepository.findByDocumentId(docId);
    }
}
