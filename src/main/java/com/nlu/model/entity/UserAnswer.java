package com.nlu.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_answers", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "student_id", "option_id" })})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name ="option_id")
	private Option option;
	@ManyToOne(optional = true)
	@JoinColumn(name ="student_id")
	private Student student;
	@ManyToOne(optional = true)
	@JoinColumn(name ="user_id")
	private User user;
}
