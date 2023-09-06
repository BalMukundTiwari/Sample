package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cms.model.Course;

@Service
@FeignClient(name = "Course-Service", url = "http://localhost:9091/course")
public interface CourseProxy {
	@GetMapping("/viewByCourseId/{courseId}")
    public ResponseEntity<Course> viewByCourseId(@PathVariable String courseId);
	
	@PutMapping("/calculateAverageFeedback/{courseId}/{rating}")
    public ResponseEntity<Course> calculateAverageFeedbackAndUpdate(@PathVariable String courseId, @PathVariable Float rating);

     @GetMapping("/viewFeedback/{courseId}")
    public ResponseEntity<?> viewFeedback(@PathVariable String courseId);
}
