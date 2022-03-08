package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.service.user.UserService;
import com.tcc.simpledocapi.service.user.form.RoleToUserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth/user")
    public Principal oauthUser(Principal principal){
        return principal;
    }

    @PostMapping("/role/addtouser")
    private ResponseEntity<?> addRoleToUser (@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}
