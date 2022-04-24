package com.tcc.simpledocapi.service.team;

import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;


public interface TeamService {

    Team createTeam(Team team , String userName);
    Page<Team> getUserTeams(int offset, int size, String userName, String teamName);
    Page<Team> getUserInvitedTeams(int offset, int size, String userName, String teamName);
    Page<Team> listPublicTeams(String name, int offset, int size);

    void deleteTeam(Long id, String username);
    Team updateTeam(Team id);

}
