package com.cms.service;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Course;
import com.cms.repository.AdmissionRepository;
import com.cms.service.*;
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
		        int fees = admissionRepository.getFeesByCourseId(admission.getCourseId());
		        totalFees += fees;
		    }
		    
//		    List<Course> registeredCourses = admissionRepository.findRegisteredCoursesByAssociateId(associateId);
//	        int totalFees = 0;
//	        for (Course course : registeredCourses) {
//	            totalFees += course.getFees();
//	        }

		    return totalFees;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		Optional<Admission> optionalAdmission = admissionRepository.findByRegistrationId(regNo);
        if (!optionalAdmission.isPresent()) {
            throw new AdmissionInvalidException("Invalid Registration Id");
        }

        // Get the admission object from the optional
        Admission admission = optionalAdmission.get();

        // Set the feedback and feedback rating
        admission.setFeedback(feedback);
//        admission.setFeedbackRating(feedbackRating);

        // Save the updated admission object to the database
        admissionRepository.save(admission);
        return admission;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		List<Admission> admissions = admissionRepository.getAdmissionsByAssociateId(associateId);
		if(admissions.isEmpty()) {
			throw new AdmissionInvalidException("AssociateId does not exists");
		}
	    int highestFee = 0;
	    List<String> coursesWithHighestFee = new ArrayList<>();
	    
	    for (Admission admission : admissions) {
	        int fees = admissionRepository.getFeesByCourseId(admission.getCourseId());
	        if (fees > highestFee) {
	            highestFee = fees;
	            coursesWithHighestFee.clear();
	            coursesWithHighestFee.add(admission.getCourseId());
	        } else if (fees == highestFee) {
	            coursesWithHighestFee.add(admission.getCourseId());
	        }
	    }
	    
	    return coursesWithHighestFee;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		List<String>feedback = admissionRepository.getFeedbackByCourseId(courseId);
		if(feedback.isEmpty()) {
			throw new AdmissionInvalidException("Invalid Course Id");
		}
		return feedback;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
		List<Admission>admissions = admissionRepository.getAdmissionsByCourseId(courseId);
		if(admissions.isEmpty()) {
			throw new AdmissionInvalidException("no admissions found in this course");
		}
		admissions.clear();
		return true;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		Admission admission = admissionRepository.getAdmissionByRegistrationId(registartionId);
		if(admission == null) {
			throw new AdmissionInvalidException("invalid registration id");
		}
		return false;
	}

	@Override
	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}

}
