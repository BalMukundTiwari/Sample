package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.proxy.CourseProxy;
import com.cms.proxy.ServiceProxy;
// import com.cms.model.Associate;
import com.cms.service.AdmissionServiceImpl;
import com.cms.model.*;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/admission")
@CrossOrigin(origins = "", allowedHeaders="")
public class AdmissionController {
	
	    @Autowired
	    AdmissionServiceImpl admissionService;
	    
	    @Autowired
		private CourseProxy courseProxy;
	    
	    @Autowired
		private ServiceProxy associateProxy;
	    @Autowired
		private RabbitTemplate rabbitTemplate;
		@Autowired
		private Queue queue;

		@Autowired
    private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
    private String sender;
		
	    @PostMapping("/register/{associateId}/{courseId}")
		public ResponseEntity<Admission> registerAssociateForCourse(@RequestBody Admission admission, @PathVariable String associateId, @PathVariable String courseId) throws AdmissionInvalidException{
			ResponseEntity<Course> course= courseProxy.viewByCourseId(courseId);
			ResponseEntity<Associate> associate= associateProxy.viewByAssociateId(associateId);
			
			if(course.getStatusCode() == HttpStatus.OK && associate.getStatusCode() == HttpStatus.OK) {
//				return ResponseEntity.ok( admissionServiceImpl.registerAssociateForCourse(admission));
				Admission registeredAdmission =  admissionService.registerAssociateForCourse(admission);
				
				if (registeredAdmission != null) {
	                String message = "Registration Details:\n" +
	                        "Associate ID: " + associateId + "\n" +
	                        "Course ID: " + courseId + "\n" +
	                        "Registration Number: " + registeredAdmission.getRegistrationId();

	                // Send registration details to the RabbitMQ queue
	                // rabbitTemplate.convertAndSend(queue.getName(), message);

//	                 Send registration email to the associate
	               sendRegistrationEmail(associate.getBody().getAssociateEmailId(), associateId, courseId,
	                        registeredAdmission.getRegistrationId());

	                return ResponseEntity.ok(registeredAdmission);
	            } else {
//	                 Return internal server error response if registration fails
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	            }
			}else {
	            // Return not found response if either course or associate is not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
			
		}
	   @PutMapping("/calculateFees/{associateId}")
	    public ResponseEntity<Integer> calculateFees(@PathVariable("associateId") String associateId) {
	        try {
	            int fees = admissionService.calculateFees(associateId);
	            return ResponseEntity.ok(fees);
	        } catch (AdmissionInvalidException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }

		@PostMapping("/feedback/{regNo}/{feedback}/{feedbackRating}")
		public Admission addFeedback(@PathVariable Long regNo,@PathVariable String feedback,@PathVariable  float feedbackRating) throws AdmissionInvalidException {
			Admission admiss =admissionService.addFeedback(regNo, feedback, feedbackRating);
			courseProxy.calculateAverageFeedbackAndUpdate(admiss.getCourseId(), feedbackRating);
			return admiss;
		}

	    @GetMapping("/highestFee/{associateId}")
	    public ResponseEntity<Course> getCourseWithHighestFee(@PathVariable("associateId") String associateId) {
			try{
	         Course course = admissionService.highestFeeForTheRegisteredCourse(associateId);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
	}catch(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	    }

	    @GetMapping("/viewFeedbackByCourseId/{courseId}")
	    public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable("courseId") String courseId) {
	        try {
	            List<String> feedbackList = admissionService.viewFeedbackByCourseId(courseId);
	            return ResponseEntity.ok(feedbackList);
	        } catch (AdmissionInvalidException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }

	    @DeleteMapping("/deactivate/{courseId}")
	    public ResponseEntity<Boolean> deactivateAdmission(@PathVariable("courseId") String courseId) {
	        try {
	            boolean deactivated = admissionService.deactivateAdmission(courseId);
	            return ResponseEntity.ok(deactivated);
	        } catch (AdmissionInvalidException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }
	    @GetMapping("/viewAll")
	    public ResponseEntity<List<Admission>> viewAllAssociates() {
	        List<Admission> associateList = admissionService.viewAll();
	        return ResponseEntity.ok(associateList);
	    }

		private void sendRegistrationEmail(String recipientEmail, String associateId, String courseId,long registrationNumber) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(sender);
			message.setTo(recipientEmail); // Set the recipient email address
			message.setSubject("Registration Confirmation");
			message.setText("Dear Associate,\n\nCongratulations! You have successfully registered for the course.\n\n" +
					"Registration Details:\n" +
					"Associate ID: " + associateId + "\n" +
					"Course ID: " + courseId + "\n" +
					"Registration Number: " + registrationNumber);
			javaMailSender.send(message);
	}
	
}