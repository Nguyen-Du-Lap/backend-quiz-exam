package com.nlu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nlu.model.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>{
    Exam findByExamNumbers_Id(Integer id);
    Exam findByCodeGroup(String codeGroup);
    Page<Exam> findByIsPublic(boolean isPublic, Pageable pageable);
    Page<Exam> findByCategory_NameAndIsPublic(String category, boolean isPublic, Pageable pageable);
    Page<Exam> findByTitleContainingAndIsPublic(String keyword, boolean isPublic, Pageable pageable);
    Page<Exam> findByCategory_NameAndTitleContainingAndIsPublic
            (String category, String keyword, boolean isPublic, Pageable pageable);
    
    Page<Exam> findByTeacher_Id(Long teacher_Id,Pageable pageable);
}
