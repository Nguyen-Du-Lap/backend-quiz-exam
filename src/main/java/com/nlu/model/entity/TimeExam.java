package com.nlu.model.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="time_exams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeExam {
	
	@Id
	private Integer id;
	private String name;

}
