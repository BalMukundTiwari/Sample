package com.cms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements ICourseService{
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException  {
		try {
			Course exisitingCourse = courseRepository.findByCourseId((String)cObj.getCourseId());
			if(exisitingCourse !=null) {
				throw new CourseInvalidException("CourseId already Exist");
			}
			
			// Generating the courseId using the sequence
			String courseId = sequenceGeneratorService.getNextCourseId();
			cObj.setCourseId(courseId);
			Course addedCourse = courseRepository.save(cObj);
			log.info("This method addCourse has completed successfully");
			return addedCourse;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error in addCourse: {}",e.getMessage());
			throw e;
		}
	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		try {
			Course exisitingCourse = courseRepository.findByCourseId(courseId);
			if(exisitingCourse ==null) {
				throw new CourseInvalidException("courseId does not exist");
			}
			exisitingCourse.setFees(fees);
			Course updateCourse = courseRepository.save(exisitingCourse);
			log.info("This method updateCourse has completed sucessfully");
			return updateCourse;
		} catch (Exception e) {
			log.error("Error in updateCourse: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		try {
			Course course = courseRepository.findByCourseId(courseId);
			if(course == null) {
				throw new CourseInvalidException("Invalid courseId");
			}
			log.info("The method viewByCourseId has completed successfully");
			return course;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error in viewByCourseId: {}",e.getMessage());
			throw e;
		}
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		try {
			Course course = courseRepository.findByCourseId(courseId);
			if(course == null) {
				throw new CourseInvalidException("CourseId does not exist");
			}
			float currentRating = course.getRating();
            int feedbackCount = 1; // Assuming we have received one new feedback

            float averageRating = (currentRating + rating) / (feedbackCount + 1);
            course.setRating(averageRating);
			
			Course updateCourse = courseRepository.save(course);
            log.info("The method calculateAverageFeedbackAndUpdate has completed successfully");
            return updateCourse;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error in calculateAverageFeedbackAndUpdate: {}", e.getMessage());
            throw e;
		}
	}

		
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		try {
            Course course = courseRepository.findByCourseId(courseId);
            if (course == null) {
                throw new CourseInvalidException("CourseId does not exist");
            }
            // Retrieve feedback rating for the course
//            float rating = courseRepository.findByFeedbackRating(courseId);
            float rating = course.getRating();
            log.info("The method findFeedbackRatingForCourseId has completed successfully");
            return rating;
        } catch (Exception e) {
            log.error("Error in findFeedbackRatingForCourseId: {}", e.getMessage());
            throw e;
        }
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		try {
            Course course = courseRepository.findByCourseId(courseId);
            if (course == null) {
                throw new CourseInvalidException("CourseId does not exist");
            }
            Course deactivatedCourse = courseRepository.save(course);
            log.info("The method deactivateCourse has completed successfully");
            return deactivatedCourse;
        } catch (Exception e) {
            log.error("Error in deactivateCourse: {}", e.getMessage());
            throw e;
        }
		
	}

	@Override
	public List<Course> viewAll() {
		try {
			List<Course> course = courseRepository.findAll();
			log.info("This method viewAll has completed successfully");
			return course;
		} catch (Exception e) {
			log.error("Error in viewAll: {}", e.getMessage());
			throw e;
		}
	}
	

}