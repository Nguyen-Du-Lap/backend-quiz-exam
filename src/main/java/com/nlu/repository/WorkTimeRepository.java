package com.nlu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.model.entity.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long>{

    WorkTime findByStudent_Id(Long idStudent);

    WorkTime findByUser_IdAndExamNumber_Id(Long userId, Integer examNumberId);
    List<WorkTime> findAllByUser_IdOrderByBeginExamDesc(Long id);

	List<WorkTime> findByStudentIdIn(List<Long> collect);
   WorkTime findByStudent_IdAndExamNumber_Id(Long students, Integer examNumberId);
}
