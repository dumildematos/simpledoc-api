package com.tcc.simpledocapi.service.team;

import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.TeamRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;


    @Override
    public Team createTeam(Team team , String userName) {
        User user = userRepository.findByUsername(userName);
        Team createdTeam = teamRepository.save(team);
        user.getTeams().add(createdTeam);
        return createdTeam;
    }

    @Override
    public Page<Team> getUserTeams(int offset, int size, String username) {
        User user = userRepository.findByUsername(username);
        return teamRepository.findTeamsByUser(user.getId(), PageRequest.of(offset, size));
    }

    @Override
    public Page<Team> getUserInvitedTeams(int offset, int size, String userName) {
        return teamRepository.findInvitedTeamByUsername(userName,  PageRequest.of(offset, size));
    }

    @Override
    public Page<Team> listPublicTeams(int offset, int size) {
        return teamRepository.findAllPublicTeams(PageRequest.of(offset, size));
    }


    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteUserTeamFromRelation(id);
        teamRepository.deleteById(id);
    }

    @Override
    public Team updateTeam(Team team) {
        return null;
    }
}
