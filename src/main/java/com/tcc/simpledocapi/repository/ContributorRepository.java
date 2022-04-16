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


}
