package com.nlu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.model.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
 	@Query("SELECT new Student(s.id, s.code, s.fullname, s.email) " +
			"FROM Student s " +
			"WHERE s.codeGroup = :codeGroup")
	List<Student> findByCodeGroup(String codeGroup);
	 @Query("SELECT w.examNumber.id " +
			 "FROM Student s " +
			 "JOIN WorkTime w ON s.id = w.student.id " +
			 "WHERE s.id = :idStudent")
	Long findIdExamByIdStudent(Long idStudent);
	 
	Optional<Student> findByEmailAndCodeGroupAndActiveTrue(String email, String codeGroup);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Student SET active = false WHERE codeGroup = :codeGroup")
	void revokeStudentsByCodeGroup(String codeGroup);
}
