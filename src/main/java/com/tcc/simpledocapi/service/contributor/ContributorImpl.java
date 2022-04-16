package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.Contributor;
import com.tcc.simpledocapi.entity.Document;
import com.tcc.simpledocapi.entity.InvitedTeam;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.ContributorRepository;
import com.tcc.simpledocapi.repository.DocumentRepository;
import com.tcc.simpledocapi.repository.InvitedTeamRepo;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContributorImpl implements ContributorService{

    private final ContributorRepository contributorRepository;
    private final DocumentRepository documentRepository;
    private final InvitedTeamRepo invitedTeamRepo;
    private final UserRepository userRepository;

    @Override
    public Contributor addDocumentContributor(Long documentId, Contributor contributor) {
        Optional<Document> document = documentRepository.findById(documentId);
        User user = userRepository.findByUsername(contributor.getUsername());
        Contributor cont = contributorRepository.save(contributor);
        InvitedTeam invitedTeam = new InvitedTeam(null, user.getId(), contributor.getTeamId());
        invitedTeamRepo.save(invitedTeam);
        document.get().getContributors().add(cont);
        return  cont;
    }

    @Override
    public Contributor findContributorByUsername(String username) {
        return contributorRepository.findByUsername(username);
    }

    @Override
    public void deleteContributor(Long contrId, Long docId) {
        documentRepository.deleteDocumentContributorRelation(docId, contrId);
        contributorRepository.deleteContributorFromDocument(contrId, docId);
    }
}
