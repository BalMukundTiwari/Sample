package com.cms.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.config.RabbitMQConfig;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.proxy.CourseProxy;
import com.cms.proxy.ServiceProxy;
import com.cms.service.AdmissionServiceImpl;
import com.cms.model.Course;
import com.cms.model.Associate;
@RestController
//@RequestMapping("/admission")
public class AdmissionController {
	
	@Autowired
	private AdmissionServiceImpl admissionServiceImpl;
	
	@Autowired
	private CourseProxy courseProxy;
	@Autowired
	private ServiceProxy associateProxy;
	
	@PostMapping("/admission/register/{asscociateId}/{courseId}")
	public ResponseEntity<Admission> registerAssociateForCourse(@RequestBody Admission admission,@PathVariable String associateId, @PathVariable String courseId) throws AdmissionInvalidException{
		ResponseEntity<Course> course= courseProxy.viewByCourseId(courseId);
		ResponseEntity<Associate> associate= associateProxy.viewByAssociateId(courseId);
		if(course.getStatusCode() != HttpStatus.OK && associate.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		
		
		return ResponseEntity.ok( admissionServiceImpl.registerAssociateForCourse(admission));
	}
	
	@PutMapping("/admission/calculateFees/{associateId}")
	public int calculateFees(@PathVariable String associateId) throws AdmissionInvalidException{
		return admissionServiceImpl.calculateFees(associateId);
	}
	
	@PostMapping("/admission/feedback/{registrationId}/{feedback}/{rating}")
	public Admission addFeedback(@PathVariable Long regisrationId,String feedback, float rating) throws AdmissionInvalidException{
		return admissionServiceImpl.addFeedback(regisrationId, feedback, rating);
		
	}
	
	@GetMapping("/admission/highestFee/{associateId}")
	public List<String>highestFeeForTheRegisteredCourse(@PathVariable String associateId) throws AdmissionInvalidException{
		return admissionServiceImpl.highestFeeForTheRegisteredCourse(associateId);
	}
	
	@GetMapping("/admission/viewFeedbackByCourseId/{courseId}")
	public List<String>viewFeedbackByCourseId(@PathVariable String courseId) throws AdmissionInvalidException{
		return admissionServiceImpl.viewFeedbackByCourseId(courseId);
	}
	
	@DeleteMapping("/admission/deactivate/{courseId}")
	public boolean deactivateCourse(@PathVariable String courseId) throws AdmissionInvalidException {
		return admissionServiceImpl.deactivateAdmission(courseId);
	}
	
	@PostMapping("/admission/makePayment/{registrationId}/{fees}")
	public boolean makePayment(@PathVariable int fees) throws AdmissionInvalidException{
		return admissionServiceImpl.makePayment(fees);
	}
	
	@GetMapping("/admission/viewAll")
	public List<Admission>viewAll(){
		return admissionServiceImpl.viewAll();
	}
	
	
	
}
