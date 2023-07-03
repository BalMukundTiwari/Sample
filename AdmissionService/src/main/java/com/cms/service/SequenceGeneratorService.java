package com.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import org.springframework.data.mongodb.core.FindAndModifyOptions;

import com.cms.model.Admission;
import com.cms.model.DatabaseSequence;


@Component
public class SequenceGeneratorService {
	
    @Autowired
    private MongoOperations mongoOperations;

    public long getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        DatabaseSequence counter = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
        if (counter == null) {
            counter = new DatabaseSequence();
            counter.setId(sequenceName);
            counter.setSeq(300L); // Set initial value here
            mongoOperations.save(counter);
        }
        return counter.getSeq();
    }
    
    public void setRegistrationId(Admission admission) {
        admission.setRegistrationId(getNextSequence(Admission.SEQUENCE_NAME));
    }
    
}





