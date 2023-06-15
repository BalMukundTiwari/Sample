package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.model.Associate;

@Repository
public interface AssociateRepository extends MongoRepository<Associate, String> {
    public Associate findByAssociateId(String associateId);
}
