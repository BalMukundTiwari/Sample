package com.cms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Course;
import com.cms.payment.PaypalService;
import com.cms.proxy.CourseProxy;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;
	
	@Autowired
	private SequenceGeneratorService seqGen;
	
	@Autowired
	CourseProxy courseProxy;
    @Autowired
	PaypalService paypalService;

	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		
		if(admissionRepository.existsByRegistrationId(admission.getRegistrationId())) {
			throw new AdmissionInvalidException("admission with given associate id already exists");
		}
		
		long registrationId = seqGen.getNextAssociateId();
		admission.setRegistrationId(registrationId);
		admissionRepository.save(admission);
		return admission;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		if(associateId == null || associateId.isEmpty()) {
			 throw new AdmissionInvalidException("AssociateId does not exist");
		}
		 List<Admission> admissions = admissionRepository.findByAssociateId(associateId);
		    if (admissions.isEmpty()) {
		        throw new AdmissionInvalidException("No admissions found for the given associate ID");
		    }

		    int totalFees = 0;
		    for (Admission admission : admissions) {
			   int fees = admission.getFees();
		        totalFees += fees;
		    }
		    
		     return totalFees;
	
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {

		
		Admission admiss=admissionRepository.findById(regNo).orElse(null);
		if(admiss==null)
		{
			throw new AdmissionInvalidException("Invalid Registration Id");
		}
		admiss.setFeedback(feedback);
		admissionRepository.save(admiss);
		return admiss;

	}

	public Course highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		
		List<Course> registeredCourses = admissionRepository.findRegisteredCoursesByAssociateId(associateId);

        Course highFeeCourse = null;
        int lowestFee = Integer.MIN_VALUE;
        for (Course course : registeredCourses) {
            int courseFee = course.getFees();
            if (courseFee > lowestFee) {
                lowestFee = courseFee;
                highFeeCourse = course;
            }
        }
        return highFeeCourse;
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		List<String>feedback = admissionRepository.getFeedbackByCourseId(courseId);
		if(feedback.isEmpty()) {
			throw new AdmissionInvalidException("Invalid Course Id");
		}
		return feedback;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
		
		List<Admission> admissions = admissionRepository.getAdmissionsByCourseId(courseId);
        if (admissions.isEmpty()) {
            throw new AdmissionInvalidException("No admissions found for this course");
        }
        admissionRepository.deleteAll(admissions);
        return true;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		try {
            // payPalService.makePayment(fees, "USD");
            // Handle any post-payment logic or success notifications
            // ...
			return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to make payment");
        }
	}

	@Override
	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}	
}
