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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.proxy.CourseProxy;
import com.cms.service.AdmissionServiceImpl;

@RestController
@RequestMapping("/admission")
public class AdmissionController {

	@Autowired
	CourseProxy courseProxy;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	AdmissionServiceImpl admissionService;

	@PostMapping("/register/{associateId}/{courseId}")
	public ResponseEntity<?> registerAssociateForCourse(@PathVariable String associateId, @PathVariable String courseId,
			@RequestBody Admission admission) throws AdmissionInvalidException {

		ResponseEntity<Associate> response = restTemplate.getForEntity(
				"http://localhost:7777/associate/viewByAssociateId/{associateId}", Associate.class, associateId);

		ResponseEntity<Course> courseResponse = courseProxy.viewByCourseId(courseId);
		admission.setCourseId(courseId);
		admission.setAssociateId(associateId);
		try {
			if (response.getStatusCode() == HttpStatus.OK && courseResponse.getStatusCode() == HttpStatus.OK) {
				Admission result = admissionService.registerAssociateForCourse(admission);
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@PutMapping("/calculateFees/{associateId}")
	public ResponseEntity<Integer> calculateFees(@PathVariable String associateId) {
		try {
			int fees = admissionService.calculateFees(associateId);
			return ResponseEntity.ok(fees);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/feedback/{regNo}/{feedback}/{feedbackRating}")
	public ResponseEntity<?> addFeedback(@PathVariable long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating) {
		
		try {
			Admission admission = admissionService.addFeedback(regNo, feedback, feedbackRating);
			return ResponseEntity.ok(admission);
		} catch (AdmissionInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	@GetMapping("/highestFee/{associateId}")
	public ResponseEntity<?> getLowestFeeCourse(@PathVariable String associateId) {
		try {
			List<String> highestFeeCourse = admissionService.lowestFeeForTheRegisteredCourse(associateId);
			if (highestFeeCourse != null) {
				return ResponseEntity.ok(highestFeeCourse);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/viewFeedbackByCourseId/{courseId}")
	public ResponseEntity<?> viewFeedbackByCourseId(@PathVariable String courseId) {
		try {
			List<String> feedbackList = admissionService.viewFeedbackByCourseId(courseId);
			return ResponseEntity.ok(feedbackList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/deactivate/{courseId}")
	public ResponseEntity<?> deactivateAdmissions(@PathVariable String courseId) {
		try {
			boolean isDeactivateAdmission = admissionService.deactivateAdmission(courseId);
			if (isDeactivateAdmission) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/makePayment/{registrationId}/{fees}")
	public ResponseEntity<?> makePayment(@PathVariable String registrationId, @PathVariable int fees) {
		try {
			// admissionService.makePayment(registrationId, fees);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/viewAll")
	public ResponseEntity<?> viewAllAdmissions() {

		try {
			List<Admission> admissions = admissionService.viewAll();
			return ResponseEntity.ok(admissions);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
