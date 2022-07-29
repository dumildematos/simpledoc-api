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
import java.util.Optional;

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
                new ArrayList<>(),
                new ArrayList<>());
        return  ResponseEntity.created(uri).body(teamService.createTeam(team, principal.getName()));
    }

    @GetMapping(value = "/teams/user", params = {"page", "size", "name"})
    public ResponseEntity<Page<Team>> getUserTeams(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> name,  Principal principal){
        return ResponseEntity.ok().body(teamService.getUserTeams(page, size, principal.getName(), name.orElse("_")));
    }

    @GetMapping(value = "/teams/user/invited", params = {"page", "size", "name"})
    public ResponseEntity<Page<Team>> getUserInvitedTeams(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> name, Principal principal){
        return ResponseEntity.ok().body(teamService.getUserInvitedTeams(page, size, principal.getName(), name.orElse("_")));
    }

    @GetMapping(value = "/teams/public", params = {"page", "size", "name"})
    public ResponseEntity<Page<Team>> getPublicTeams(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> name){
        return ResponseEntity.ok().body(teamService.listPublicTeams(name.orElse("_"), page, size));
    }

    @GetMapping(value = "/teams", params = {"page", "size"})
    public ResponseEntity<Page<Team>> getTeams(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> name){
        return ResponseEntity.ok().body(teamService.listTeams(page, size));
    }

    @DeleteMapping(value ="/team/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable(value = "teamId") Long teamId, Principal principal){
        teamService.deleteTeam(teamId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/team/{teamId}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @RequestBody CreateTeamForm form){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/team/{teamId}").toUriString());
        Team team = new Team(teamId,
                form.getName(),
                form.getDescription(),
                LocalDateTime.now(),
                form.getBanner(),
                form.getType() ,
                new ArrayList<>(),
                new ArrayList<>());
        return ResponseEntity.created(uri).body(teamService.updateTeam(team));
    }
}
