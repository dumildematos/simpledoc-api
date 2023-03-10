package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.ConfirmationToken;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;
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
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class ConfirmationController {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;



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
                userService.saveUser(user, "ROLE_USER");
                modelAndView.setViewName("confirmation-success");
            }
        }


        /**/

        return modelAndView;
    }

}
