package com.cms.service;


import java.util.List;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;


public class AdmissionServiceImpl implements IAdmissionService {

	
	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		
		return null;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		return null;
	}

	public List<String> lowestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		return null;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		return null;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
				
		return false;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Admission> viewAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
