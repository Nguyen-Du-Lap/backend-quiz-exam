package com.nlu.model.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="work_times")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
	@ManyToOne(optional = false)
	@JoinColumn(name = "examnumber_id")
	private ExamNumber examNumber;
	
	@JsonBackReference
	@ManyToOne(optional = true)
	@JoinColumn(name = "student_id")
	private Student student;
	
	@JsonBackReference
	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="begin_exam")
	private Timestamp beginExam;
	
	@Column(name="end_exam")
	private Timestamp endExam;

}
