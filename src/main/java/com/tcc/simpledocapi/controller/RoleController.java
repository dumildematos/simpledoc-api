package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.save(role));
    }

    @GetMapping(value = "/roles", params = {"page","size"})
    public ResponseEntity<Page<Role>> listRoles(@RequestParam("page") int page, @RequestParam("size") int size){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/list").toUriString());
        return ResponseEntity.ok().body(roleService.getRoles(page, size));
    }

}
