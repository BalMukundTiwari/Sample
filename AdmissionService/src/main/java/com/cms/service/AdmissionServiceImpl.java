package com.cms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;
	
	@Autowired
	private SequenceGeneratorService seqGen;
	

	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		
		if(admissionRepository.existsByRegistrationId(admission.getRegistrationId())) {
			throw new AdmissionInvalidException("admission with given associate id already exists");
		}
		
		long registrationId = seqGen.getNextSequence(Admission.SEQUENCE_NAME);
		admission.setRegistrationId(registrationId);
		admissionRepository.save(admission);
		return admission;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		return 0;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		return null;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		return null;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		return null;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
				
		return false;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		return false;
	}

	@Override
	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}

}