package com.cms.service;


import java.util.List;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;

public class CourseServiceImpl implements ICourseService{
	
	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException  {
		
			return null;
	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		return null;
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		return null;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		return null;
	}

		
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		return 0;
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		
			return null;
		
	}

	@Override
	public List<Course> viewAll() {
		
		return null;
	}
	

}
