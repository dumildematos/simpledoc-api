package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Document;
import com.tcc.simpledocapi.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "SELECT * from document d inner join team_documents td on  d.id = td.documents_id and td.team_id = ?1", nativeQuery = true)
    Page<Document> findDocumentsByTeam(@Param("teamId") Long teamId, Pageable pageable);

    @Modifying
    @Query(value = "delete from document_contributors where document_contributors.document_id = ?1 and document_contributors.contributors_id = ?2", nativeQuery = true)
    void deleteDocumentContributorRelation(@Param("docId") Long docId, @Param("contrId") Long contrId);

    @Query(value = "select count(dc.id) from document dc inner join team_documents tdc on dc.id = tdc.documents_id inner join team t on t.id = tdc.team_id inner join user_teams on user_teams.teams_id = t.id and user_teams.user_id =?1", nativeQuery = true)
    Long userTotalOfDocuments(@Param("userId") Long userId);

}
