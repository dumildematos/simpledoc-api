package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {
}
