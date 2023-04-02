package com.tcc.simpledocapi.service.contributor;

import com.tcc.simpledocapi.entity.*;
import com.tcc.simpledocapi.repository.*;
import com.tcc.simpledocapi.service.email.EmailDetails;
import com.tcc.simpledocapi.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
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
    private final HttpServletRequest request;
    @Autowired
    private EmailService emailService;

    @Override
    public Contributor addDocumentContributor(Long documentId, Contributor contributor, User owner) throws MalformedURLException {

        Optional<Document> document = documentRepository.findById(documentId);

        User user = userRepository.findByUsername(contributor.getUsername());
        Optional<Team> team = teamRepository.findById(contributor.getTeamId());
        team.ifPresent(value -> user.getInvitedTeams().add(value));

        Contributor cont = contributorRepository.save(contributor);
        document.ifPresent(value -> value.getContributors().add(cont));


        URL domain = new URL(request.getRequestURL().toString());

        String idsPath = document.get().getId() + "/"+team.get().getId()+"/"+cont.getId();

        String url = request.getRequestURL().toString();
        String protocol = request.getProtocol().split("/")[0];
        String baseUrl = url.split(request.getContextPath())[0];


        String batch  = domain.toString().split("/api/v1/document")[0];
        log.info("\uD83D\uDE0C"+ batch);


        EmailDetails mailMessage = new EmailDetails(
                contributor.getUsername(),
                "Hello! <b>" + contributor.getFirstName() +" </b> , " + owner.getFirstname()  + " has invited you to work on document <i>" + document.get().getName() +" </i> of team <i> " + team.get().getName() +"</i>",
                batch + "/user/document",
                "Document Invitation!",
                null);

        emailService.sendDocumentInvitationEmail(mailMessage, idsPath);


        return  cont;
    }

    @Override
    public Contributor findContributor(String username, Long docId) {
        return contributorRepository.findContributor(username, docId);
    }

    @Override
    public Optional<Contributor> findById(Long id) {
        return contributorRepository.findById(id);
    }

    @Override
    public void deleteContributor(Long contrId, Long docId) {
        documentRepository.deleteDocumentContributorRelation(docId, contrId);
        contributorRepository.deleteContributorFromDocument(contrId, docId);
        // teamRepository.findById(docId);
    }

    @Override
    public void deteleContributorByDocumentTeamUserId(Long documentId, Long teamId, Long userId) {

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> contributorRepository.deleteContributorByIds(documentId, teamId, value.getUsername()));

    }

    @Override
    public void save(Contributor contributor) {
        contributorRepository.save(contributor);
    }

    @Override
    public Collection<Contributor> listDocumentContributors(Long docId) {
        return contributorRepository.findByDocumentId(docId);
    }
}
