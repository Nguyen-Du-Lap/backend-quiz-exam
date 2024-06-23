package com.nlu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nlu.model.entity.TimeExam;
import com.nlu.repository.TimeExamRepository;

@Service

public class TimeExamService {

	@Autowired
	private TimeExamRepository timeExamRepository;
	
	public List<TimeExam> getAllTimeExam(){
		return timeExamRepository.findAll();
	}
}
