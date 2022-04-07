package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.service.team.TeamService;
import com.tcc.simpledocapi.service.team.form.CreateTeamForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/team/create")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamForm form, Principal principal) throws IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/team/create").toUriString());
        Team team = new Team(null,
                form.getName(),
                form.getDescription(),
                LocalDateTime.now(),
                form.getBanner(),
                form.getType() ,
                new ArrayList<>());
        return  ResponseEntity.created(uri).body(teamService.createTeam(team, principal.getName()));
    }

    @GetMapping(value = "/teams/user", params = {"page", "size"})
    public ResponseEntity<Page<Team>> getUserTeams(@RequestParam int page, @RequestParam int size, Principal principal){
        return ResponseEntity.ok().body(teamService.getUserTeams(page, size, principal.getName()));
    }

    @GetMapping(value = "/teams/user/invited", params = {"page", "size"})
    public ResponseEntity<Page<Team>> getUserInvitedTeams(@RequestParam int page, @RequestParam int size, Principal principal){
        return ResponseEntity.ok().body(teamService.getUserInvitedTeams(page, size, principal.getName()));
    }

    @GetMapping(value = "/teams/public", params = {"page", "size"})
    public ResponseEntity<Page<Team>> gePulicTeams(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok().body(teamService.listPublicTeams(page, size));
    }

    @DeleteMapping(value ="/team/delete", params = {"id"})
    public ResponseEntity<?> deleteTeam(@RequestParam Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
