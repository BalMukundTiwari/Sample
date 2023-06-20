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
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Admission;
import com.cms.service.IAdmissionService;

@RestController
public class AdmissionController {
    @Autowired
	private  IAdmissionService admissionService;

  

    @PostMapping("/enroll/{associateId}/{courseId}")
    public ResponseEntity<String> enrollAssociateForCourse(
            @PathVariable String associateId,
            @PathVariable String courseId
    ) {
        try {
            admissionService.registerAssociateForCourse(associateId, courseId);
            // Send registration details to RabbitMQ message broker
            // Send email to associate's email ID with registration details using JavaMailSender API
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/totalFeesPaid/{associateId}")
    public ResponseEntity<Double> calculateTotalFeesPaid(@PathVariable String associateId) {
        try {
            double totalFeesPaid = admissionService.calculateFees(associateId);
            return ResponseEntity.ok(totalFeesPaid);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/feedback/{regNo}/{feedback}/{feedbackRating}")
    public ResponseEntity<String> addFeedback(
            @PathVariable String regNo,
            @PathVariable String feedback,
            @PathVariable int feedbackRating
    ) {
        try {
            String courseId = admissionService.addFeedback(regNo, feedback);
            // Perform intermicroservice communication with Course service to calculate average feedback
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/lowestFee/{associateId}")
    public ResponseEntity<String> getCourseWithLowestFee(@PathVariable String associateId) {
        try {
            String courseWithLowestFee = admissionService.lowestFeeForTheRegisteredCourse(associateId);
            return ResponseEntity.ok(courseWithLowestFee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewFeedbackByCourseId/{courseId}")
    public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable String courseId) {
        try {
            List<String> feedbackList = admissionService.viewFeedbackByCourseId(courseId);
            return ResponseEntity.ok(feedbackList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<String> deactivateAdmission(@PathVariable String courseId) {
        try {
            admissionService.deactivateAdmission(courseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/makePayment/{registrationId}/{fees}")
    public ResponseEntity<String> makePayment(
            @PathVariable String registrationId,
            @PathVariable double fees
    ) {
        try {
            admissionService.makePayment(registrationId, fees);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Admission>> viewAllAdmissions() {
        try {
            List<Admission> admissions = admissionService.viewAll();
            return ResponseEntity.ok(admissions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
}
