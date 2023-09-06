package com.cms.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Course;

public interface IAdmissionService {
	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException;
	public int calculateFees(String associateId)throws AdmissionInvalidException;
	public Admission addFeedback(Long regNo,String feedback,float feedbackRating) throws AdmissionInvalidException;
	public Course highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException;
	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException;
	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException;
	public boolean makePayment(int registartionId)throws AdmissionInvalidException;
	public List<Admission> viewAll();
}
