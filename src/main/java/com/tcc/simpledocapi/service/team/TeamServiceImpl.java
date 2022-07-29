package com.tcc.simpledocapi.service.team;

import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.ContributorRepository;
import com.tcc.simpledocapi.repository.TeamRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ContributorRepository contributorRepository;


    @Override
    public Team createTeam(Team team , String userName) {
        User user = userRepository.findByUsername(userName);
        Team createdTeam = teamRepository.save(team);
        user.getTeams().add(createdTeam);
        return createdTeam;
    }

    @Override
    public Page<Team> getUserTeams(int offset, int size, String username, String teamName) {
        User user = userRepository.findByUsername(username);
        Page<Team> teams = teamRepository.findTeamsByUser(user.getId(), teamName, PageRequest.of(offset, size));
       teams.getContent().stream().forEach(team -> {
             team.getContributors().addAll(contributorRepository.findTeamContributors(team.getId()));
        });
        return teams;
    }

    @Override
    public Page<Team> getUserInvitedTeams(int offset, int size, String userName, String teamName) {
        Page<Team> teams = teamRepository.findInvitedTeamByUsername(userName, teamName, PageRequest.of(offset, size));
        teams.getContent().stream().forEach(team -> {
            team.getContributors().addAll(contributorRepository.findTeamContributors(team.getId()));
        });
        return teams;
    }

    @Override
    public Page<Team> listPublicTeams(String name, int offset, int size) {
        return teamRepository.findAllPublicTeams(name, PageRequest.of(offset, size));
    }

    @Override
    public Page<Team> listTeams(int offset, int size) {
        return teamRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public void deleteTeam(Long id, String username) {
        User user = userRepository.findByUsername(username);
        Optional<Team> team = teamRepository.findById(id);
        user.getTeams().remove(team.get().getId());
        userRepository.save(user);
        userRepository.deleteUserTeamAssociation(id);
        teamRepository.deleteById(id);
    }

    @Override
    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }
}
