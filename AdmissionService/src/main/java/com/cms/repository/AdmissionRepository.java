package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.model.Admission;
import com.cms.model.Course;

@Repository
public interface AdmissionRepository extends MongoRepository<Admission,Long>{

	boolean existsByRegistrationId(long registrationId);

	List<Admission> findByAssociateId(String associateId);

	int getFeesByCourseId(String courseId);

	List<Course> findRegisteredCoursesByAssociateId(String associateId);

	Optional<Admission> findByRegistrationId(Long regNo);

	List<Admission> getAdmissionsByAssociateId(String associateId);

	List<String> getFeedbackByCourseId(String courseId);

	List<Admission> getAdmissionsByCourseId(String courseId);

	Admission getAdmissionByRegistrationId(Integer registartionId);

}
