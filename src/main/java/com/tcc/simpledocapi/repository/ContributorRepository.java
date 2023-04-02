package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Contributor findByUsername(String username);

    Collection<Contributor> findByDocumentId(Long docId);

    @Modifying
    @Query(value = "delete from contributor where contributor.id = ?1 and contributor.document_id = ?2", nativeQuery = true)
    void deleteContributorFromDocument(@Param("contrId") Long contrId,  @Param("docId") Long docId);

    @Query(value = "SELECT * from contributor where contributor.username = ?1 and contributor.document_id = ?2 ", nativeQuery = true)
    Contributor findContributor(@Param("username") String username,  @Param("docId") Long docId);

    @Query(value = "select c.id, c.avatar, c.document_id, c.first_name, c.last_name, c.role, c.team_id, c.username, c.status from contributor c inner join team_documents td on c.document_id = td.documents_id and c.status = 1 inner join team t on td.team_id = t.id and t.id =?1", nativeQuery = true)
    Collection<Contributor> findTeamContributors(@Param("teamId") Long teamId);

    @Modifying
    @Query(value = "delete from contributor where contributor.id = ?1 and contributor.team_id = ?2 and contributor.username = ?3", nativeQuery = true)
    void deleteContributorByIds(@Param("docId") Long docId, @Param("teamId") Long teamId, @Param("username") String username);

}
