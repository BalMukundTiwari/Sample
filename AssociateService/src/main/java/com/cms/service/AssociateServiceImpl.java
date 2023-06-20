package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class AssociateServiceImpl implements IAssociateService{
	
	@Autowired
	private final AssociateRepository associateRepository = null;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException{
		try {
			Associate exisitingAssociate = associateRepository.findByAssociateId(cObj.getAssociateId());
			if (exisitingAssociate != null) {
				throw new AssociateInvalidException("AssociateId already Exist");
			}
			String associateId = sequenceGeneratorService.getNextAssociateId();
			cObj.setAssociateId(associateId);
			Associate addedAssociate = associateRepository.save(cObj);
			log.info("This method addAssociate has completed successfully");
			return addedAssociate;
		} catch (Exception e) {
			log.error("Error in addAssociate: {}", e.getMessage());
			throw e;
		}
	}
	@Override
	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		// TODO Auto-generated method stub
		try {
			Associate associate = associateRepository.findByAssociateId(associateId);
			if (associate == null) {
				throw new AssociateInvalidException("Invalid AssociateId");
			}
			log.info("The method viewByAssociateId has completed successfully");
			return associate;
		} catch (Exception e) {
			log.error("Error in viewByAssociateId: {}", e.getMessage());
			throw e;
		}
	}
	@Override
	public Associate updateAssociate(String associateId, String associateAddress)throws AssociateInvalidException {
		try {
			Associate exisitingAssociate = associateRepository.findByAssociateId(associateId);
			if (exisitingAssociate == null) {
				throw new AssociateInvalidException("associateId does not exist");
			}
			exisitingAssociate.setAssociateAddress(associateAddress);
			Associate updateAssociate = associateRepository.save(exisitingAssociate);
			log.info("This method updateAssociate has completed sucessfully");
			return updateAssociate;
		} catch (Exception e) {
			log.error("Error in updateAssociate: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Associate> viewAll() {
		// TODO Auto-generated method stub
		try {
			List<Associate> associateList = associateRepository.findAll();
			log.info("This method viewAll has completed successfully");
			return associateList;
		} catch (Exception e) {
			log.error("Eror in viewAll: {}", e.getMessage());
			throw e;
		}
	}

}