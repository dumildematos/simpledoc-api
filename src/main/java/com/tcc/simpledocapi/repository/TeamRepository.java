package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "SELECT * from team t inner join user_teams ut on  t.id = ut.teams_id and ut.user_id = ?1", nativeQuery = true)
    Page<Team> findTeamsByUser(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select * from team t inner join contributor cnt on cnt.team_id = t.id and cnt.username = ?1", nativeQuery = true)
    Page<Team> findInvitedTeamByUsername(@Param("username") String username, Pageable pageable);

}
