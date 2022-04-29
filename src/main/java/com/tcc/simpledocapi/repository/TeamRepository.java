package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "SELECT * from team t inner join user_teams ut on  t.id = ut.teams_id and ut.user_id = ?1 and t.name like concat('%',?2, '%')", nativeQuery = true)
    Page<Team> findTeamsByUser(@Param("userId") Long userId, @Param("name") String name, Pageable pageable);

    @Query(value = "select distinct  * from team t  join user_invited_teams it join contributor cnt on t.id = it.invited_teams_id and cnt.username = ?1 and t.name like concat('%',?2, '%') group by t.id", nativeQuery = true)
    Page<Team> findInvitedTeamByUsername(@Param("username") String username,  @Param("name") String name, Pageable pageable);

    @Query(value = "select * from team t where t.type = 'PUBLIC' and t.name like concat('%',?1, '%')", nativeQuery = true)
    Page<Team> findAllPublicTeams( @Param("name") String name, Pageable pageable);

    @Modifying
    @Query(value = "delete from team_documents  where team_documents.team_id = ?1 and team_documents.documents_id = ?2", nativeQuery = true)
    void deleteTeamDocumentRelation(@Param("teamId") Long teamId, @Param("docId") Long docId);

}
