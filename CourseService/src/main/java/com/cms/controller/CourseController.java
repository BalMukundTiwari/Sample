package com.cms.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Course;
import com.cms.service.ICourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
    private  ICourseService courseService;

  
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course addedCourse = courseService.addCourse(course);
            return ResponseEntity.ok(addedCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
 
    @PutMapping("/update/{courseId}/{courseFees}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @PathVariable Integer courseFees) {
        try {
            Course updatedCourse = courseService.updateCourse(courseId, courseFees);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewByCourseId/{courseId}")
    public ResponseEntity<Course> viewByCourseId(@PathVariable String courseId) {
        try {
            Course course = courseService.viewByCourseId(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/viewFeedback/{courseId}")
    public ResponseEntity<?> viewFeedback(@PathVariable String courseId) {
        try {
            float feedbacks = courseService.findFeedbackRatingForCourseId(courseId);
            return ResponseEntity.ok(feedbacks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/calculateAverageFeedback/{courseId}/{rating}")
    public ResponseEntity<Course> calculateAverageFeedbackAndUpdate(@PathVariable String courseId, @PathVariable float rating) {
        try {
            Course averageFeedback = courseService.calculateAverageFeedbackAndUpdate(courseId, rating);
            return ResponseEntity.ok(averageFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/viewAll")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.viewAll();
            return ResponseEntity.ok(courses); 
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    
    
    
}