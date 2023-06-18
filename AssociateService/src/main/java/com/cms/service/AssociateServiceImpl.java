package com.cms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@Transactional
public class AssociateServiceImpl implements IAssociateService{
	
	@Autowired
    private AssociateRepository associateRepository;
	
	@Autowired
	private SequenceGeneratorService seqGenerator;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException {
	    try {
	        Associate existingAssociate = associateRepository.findByAssociateId(cObj.getAssociateId());
	        if (existingAssociate != null) {
	            throw new AssociateInvalidException("AssociateId already exists");
	        }

	        if (cObj == null) {
	            throw new AssociateInvalidException("Invalid associate data");
	        }

	        String associateId = seqGenerator.generateNextAssociateId();
	        cObj.setAssociateId(associateId);
	        Associate addedAssociate = associateRepository.save(cObj);
	        log.info("This method addAssociate has completed successfully");
	        return addedAssociate;
	    } catch (Exception e) {
	        log.error("Error in addAssociate: {}", e.getMessage());
	        throw e;
	    }
	}


	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		try {
            // Check if the given associate id exists
			Associate associate = associateRepository.findByAssociateId(associateId);
            if (associate == null) {
                throw new AssociateInvalidException("AssociateId does not exist");
            }
//            log.info("Associate updated successfully");
            return associate; // Placeholder return statement, replace with actual implementation
        } catch (Exception e) {
//            log.error("Exception occurred in updateAssociate method: {}", e.getMessage());
            throw e;
        }
	}

	public Associate updateAssociate(String associateId, String associateEmailId)throws AssociateInvalidException {
		try {
            // Check if the given associate id exists
            if (!associateRepository.existsById(associateId)) {
                throw new AssociateInvalidException("AssociateId does not exist");
            }

            Associate associate = associateRepository.findById(associateId).orElse(null);
            associate.setAssociateEmailId(associateEmailId);
            Associate updatedAssociate = associateRepository.save(associate);
//            Associate updatedAssociateDTO = convertToDTO(updatedAssociate);
//            log.info("Associate updated successfully");
            return updatedAssociate; 
        } catch (Exception e) {
//            log.error("Exception occurred in updateAssociate method: {}", e.getMessage());
            throw e;
        }
	}

	
	@Override
	public List<Associate> viewAll() {
		try {
			List<Associate> associate = associateRepository.findAll();
			log.info("This method viewAll has completed successfully");
			return associate;
		} catch (Exception e) {
			log.error("Error in viewAll: {}", e.getMessage());
			throw e;
		}
	}

}