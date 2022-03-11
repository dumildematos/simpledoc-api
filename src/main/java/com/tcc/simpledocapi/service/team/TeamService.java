package com.tcc.simpledocapi.service.team;

import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeamService {

    Team createTeam(Team team , String userName);
    Page<Team> getUserTeams(int offset, int size, String userName);
    void deleteTeam(Long teamId);
    Team updateTeam(Team team);

}
