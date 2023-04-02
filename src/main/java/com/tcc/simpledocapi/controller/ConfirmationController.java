package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.*;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;
import com.tcc.simpledocapi.service.contributor.ContributorService;
import com.tcc.simpledocapi.service.document.DocumentService;
import com.tcc.simpledocapi.service.email.ConfirmationTokenService;
import com.tcc.simpledocapi.service.team.TeamService;
import com.tcc.simpledocapi.service.template.TemplateService;
import com.tcc.simpledocapi.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class ConfirmationController {

    private final DocumentService documentService;

    private final UserService userService;
    private final TeamService teamService;
    private final ContributorService contributorService;

    private final ConfirmationTokenService confirmationTokenService;



    @GetMapping(value="/confirm-account", params = {"token"})
    public ModelAndView confirmToken(@RequestParam("token") String confirmationToken) throws MalformedURLException {
        //boolean confirmed = accountService.confirmAccount(confirmationCode);
        ModelAndView modelAndView = new ModelAndView();
        Optional<ConfirmationToken> cToken = confirmationTokenService.verify(confirmationToken);

        if(!cToken.isPresent()){
            modelAndView.setViewName("confirmation-failure");
        } else {
            Calendar cal = Calendar.getInstance();
            if((cToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                modelAndView.setViewName("confirmation-expired");
            } else {
                User user  = cToken.get().getUser();
                user.setIsEnabled(1);
                userService.updateUserWithoutPassword(user);
                modelAndView.setViewName("confirmation-success");
            }
        }


        /**/

        return modelAndView;
    }

    @GetMapping(value="/document/accept/{document_id}/{team_id}/{contributor_id}")
    public ModelAndView confirmInvitation(@PathVariable Long document_id,@PathVariable Long team_id, @PathVariable Long contributor_id) throws MalformedURLException {
        //boolean confirmed = accountService.confirmAccount(confirmationCode);

        Optional<Contributor> contributor =  contributorService.findById(contributor_id);

        Optional<Document> document = documentService.getDocumentById(document_id);
        Optional<Team> team = teamService.getTeamById(team_id);


        ModelAndView modelAndView = new ModelAndView();


        if(!contributor.isPresent() || !document.isPresent() || !team.isPresent()){
            modelAndView.setViewName("confirmation-failure");
        }else{

            //Contributor contributor = contributorService.findContributor(user.get().getUsername(), document_id);

            if(contributor.get().getStatus() == 0) {
                contributor.get().setStatus(1);
                contributorService.save(contributor.get());
                modelAndView.setViewName("confirmation-success");

            }else {
               // modelAndView.setViewName("confirmation-success");
            }
        }

        return modelAndView;
    }


    @GetMapping(value="/document/reject/{document_id}/{team_id}/{contributor_id}")
    public ModelAndView rejectInvitation(@PathVariable Long document_id,@PathVariable Long team_id, @PathVariable Long contributor_id) throws MalformedURLException {
        //boolean confirmed = accountService.confirmAccount(confirmationCode);

        Optional<Contributor> contributor =  contributorService.findById(contributor_id);
        Optional<Document> document = documentService.getDocumentById(document_id);
        Optional<Team> team = teamService.getTeamById(team_id);


        ModelAndView modelAndView = new ModelAndView();


        if(!contributor.isPresent() || !document.isPresent() || !team.isPresent()){
            modelAndView.setViewName("confirmation-failure");
        }else{

            if(contributor.get().getStatus() == 0){
                contributorService.deleteContributor(document_id, contributor_id);
                modelAndView.setViewName("confirmation-success");
            }else{

            }

        }

        return modelAndView;
    }



}
