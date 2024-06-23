package com.nlu.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name ="students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name ="code_group")
	private String codeGroup;
	@Column(nullable = false,length = 8)
	private String code;
	@Column(nullable = false,length = 20)
	private String username;
	@Column(nullable = false)
	private String password;
	private String fullname;
	private String email;
	private Boolean active;
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name="teacher_id")
	private User teacher;
	
//	@OneToMany(mappedBy = "student")
//	private Set<UserAnswer> listUserAnswers;
	@JsonManagedReference
	@OneToMany(mappedBy = "student")
	private Set<WorkTime> lisWorkTimes;
	public Student(Long id, String code, String fullname, String email) {
		this.id = id;
		this.code = code;
		this.email = email;
		this.fullname = fullname;
	}
}
