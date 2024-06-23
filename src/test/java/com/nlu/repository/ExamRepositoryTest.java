package com.nlu.repository;

import com.nlu.model.entity.Exam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExamRepositoryTest {
    @Autowired
    private ExamRepository examRepository;
    @Test
    public void testGetExams() {
            Pageable pageable = PageRequest.of(1,3);
            Page<Exam> pageExams = examRepository.findByIsPublic(true, pageable);
            List<Exam> exams = new ArrayList<Exam>();
            exams = pageExams.getContent();
            for(Exam exam : exams) {
                System.out.println(exam.getId());
                System.out.print(exam.getTitle());
                System.out.print(exam.getDescription());
                System.out.print(exam.getTimeExam().getName());
                System.out.println();
            }
            System.out.println("currentPage"+ pageExams.getNumber());
            System.out.println("totalItems"+ pageExams.getTotalElements());
            System.out.println("totalPages"+ pageExams.getTotalPages());

    }
    @Test
    public void testGetExamByCategoryAndKeyWork() {
        Pageable pageable = PageRequest.of(0,3);
        String keyword = "Tôi";
        String category = "Tin học";
        Page<Exam> pageExams = examRepository
                .findByCategory_NameAndTitleContainingAndIsPublic(category, keyword, true, pageable);
        List<Exam> exams = new ArrayList<Exam>();
        exams = pageExams.getContent();
        for(Exam exam : exams) {
            System.out.println(exam.getId());
            System.out.print(exam.getTitle());
            System.out.print(exam.getDescription());
            System.out.print(exam.getTimeExam().getName());
            System.out.println();
        }
        System.out.println("currentPage"+ pageExams.getNumber());
        System.out.println("totalItems"+ pageExams.getTotalElements());
        System.out.println("totalPages"+ pageExams.getTotalPages());
    }

    @Test
    public void testGetExamByCategory() {
        Pageable pageable = PageRequest.of(0,3);
        String category = "Tin học";
        Page<Exam> pageExams = examRepository
                .findByCategory_NameAndIsPublic(category, true, pageable);
        List<Exam> exams = new ArrayList<Exam>();
        exams = pageExams.getContent();
        for(Exam exam : exams) {
            System.out.println(exam.getId());
            System.out.print(exam.getTitle());
            System.out.print(exam.getDescription());
            System.out.print(exam.getTimeExam().getName());
            System.out.println();
        }
        System.out.println("currentPage"+ pageExams.getNumber());
        System.out.println("totalItems"+ pageExams.getTotalElements());
        System.out.println("totalPages"+ pageExams.getTotalPages());
    }

    @Test
    public void testGetExamByKeyWord() {
        Pageable pageable = PageRequest.of(0,3);
        String keyword = "Tôi";
        Page<Exam> pageExams = examRepository
                .findByTitleContainingAndIsPublic(keyword, true, pageable);
        List<Exam> exams = new ArrayList<Exam>();
        exams = pageExams.getContent();
        for(Exam exam : exams) {
            System.out.println(exam.getId());
            System.out.print(exam.getTitle());
            System.out.print(exam.getDescription());
            System.out.print(exam.getTimeExam().getName());
            System.out.println();
        }
        System.out.println("currentPage"+ pageExams.getNumber());
        System.out.println("totalItems"+ pageExams.getTotalElements());
        System.out.println("totalPages"+ pageExams.getTotalPages());
    }
}