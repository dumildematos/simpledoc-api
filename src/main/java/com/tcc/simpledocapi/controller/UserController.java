package com.tcc.simpledocapi.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.simpledocapi.entity.ConfirmationToken;
import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import com.tcc.simpledocapi.enums.Avatar;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;
import com.tcc.simpledocapi.service.document.DocumentService;
import com.tcc.simpledocapi.service.email.ConfirmationTokenService;
import com.tcc.simpledocapi.service.email.EmailDetails;
import com.tcc.simpledocapi.service.email.EmailService;
import com.tcc.simpledocapi.service.team.TeamService;
import com.tcc.simpledocapi.service.template.TemplateService;
import com.tcc.simpledocapi.service.user.UserService;
import com.tcc.simpledocapi.service.role.form.RoleToUserForm;
import com.tcc.simpledocapi.service.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final TemplateService templateService;
    private final TeamService teamService;
    private final DocumentService documentService;
    private ConfirmationTokenRepository confirmationTokenRepository;




    @GetMapping("/oauth/user")
    public Principal oauthUser(Principal principal){
        return principal;
    }

    @PostMapping("/role/adduser")
    private ResponseEntity<?> addRoleToUser (@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/register")
    private ResponseEntity<?> addUser (@RequestBody UserForm form) {
        Optional<User> existedUser = Optional.ofNullable(userService.getUser(form.getUsername()));

        if(existedUser.isPresent()) {
            HashMap<String, String> resBody = new HashMap<>();
            resBody.put("message","Username already taken");
            return ResponseEntity.ok().body(resBody);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        User user = new User(
                null,
                form.getUsername(),
                form.getPassword(),
                form.getFirstname(),
                form.getLastname(),
                Avatar.getBase(),
                LocalDate.parse(form.getBirthday(), formatter),
                form.getCountry(),
                form.getPhonenumber(),
                0,
                null,
                AuthorizationProvider.LOCAL ,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );


        return ResponseEntity.ok().body(userService.saveUser(user, form.getRole()));
    }

    @PutMapping("user/edit")
    public ResponseEntity<?> editUser(@RequestBody UserForm form){

        User oldUser = userService.getUser(form.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        User user = new User(
                form.getId(),
                form.getUsername(),
                oldUser.getPassword(),
                form.getFirstname(),
                form.getLastname(),
                form.getAvatar(),
                LocalDate.parse(form.getBirthday(), formatter),
                form.getCountry(),
                form.getPhonenumber(),
                1,
                null,
                AuthorizationProvider.LOCAL ,
                oldUser.getRoles(),
                oldUser.getTeams(),
                oldUser.getInvitedTeams(),
                oldUser.getTemplates()
        );

        return ResponseEntity.ok().body(userService.saveUser(user, form.getRole()));
    }

    @PutMapping("user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserForm form, Principal principal){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        User savedUser = userService.getUser(principal.getName());

        User newUserData = new User(
                savedUser.getId(),
                savedUser.getUsername(),
                form.getPassword(),
                savedUser.getFirstname(),
                savedUser.getLastname(),
                savedUser.getAvatar(),
                savedUser.getBirthdate(),
                savedUser.getCountry(),
                savedUser.getPhonenumber(),
                savedUser.getIsEnabled(),
                savedUser.getGender(),
                savedUser.getAuthProvider(),
                savedUser.getRoles(),
                savedUser.getTeams(),
                savedUser.getInvitedTeams(),
                savedUser.getTemplates()
        );


        return ResponseEntity.ok().body(userService.saveUser(newUserData, form.getRole()));
    }

    @GetMapping(value = "/user/list", params = {"page","size"})
    public ResponseEntity<Page<User>> listUsers(@RequestParam("page") int page, @RequestParam("size") int size){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/list").toUriString());
        return ResponseEntity.ok().body(userService.getUsers(page, size));
    }

    @GetMapping("/user/me")
    public User getUser(Principal principal){
        return userService.getUser(principal.getName());
    }

    @GetMapping("/token/refresh")
    private void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date((System.currentTimeMillis() + 10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception) {

                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping("/user/activity")
    public ResponseEntity<?> getUserActivity(Principal principal ){
        User user = userService.getUser(principal.getName());
        Long totalTemplatePrice = templateService.userTotalTemplatePrice(user.getId());
        Long totalOfTeams = teamService.getUserTotalOfTeams(user.getId());
        Long totalOfDocuments = documentService.userTotalOfDocuments(user.getId());
        Map<String, Long> activity = new HashMap<>();


        activity.put("totalTemplatePrice", totalTemplatePrice!= null ? totalTemplatePrice: 0L);
        activity.put("totalOfTeams", totalOfTeams!= null ? totalOfTeams: 0L);
        activity.put("totalOfDocuments", totalOfDocuments!= null ? totalOfDocuments: 0L);

        log.info(String.valueOf(totalTemplatePrice));

        return ResponseEntity.ok().body(activity);
    }

}
