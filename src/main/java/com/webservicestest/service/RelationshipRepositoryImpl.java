package com.webservicestest.service;

import com.webservicestest.domain.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RelationshipRepositoryImpl extends JpaRepository<Relationship,Long> {

    List<Relationship> findRelationshipsByUserOne(String userOne);
    List<Relationship> findRelationshipsByUserTwo(String userTwo);
}
