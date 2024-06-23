package com.nlu.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name ="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,length = 20)
	private String code;
	@Column(nullable = false,length = 20)
	private String username;
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	private String fullname;
	private String email;
	private Boolean active;
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
	private Set<Student> listStudents;
	
	@OneToMany(mappedBy = "teacher")
	private Set<Exam> listExams;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name ="user_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name ="role_id"))
	private Set<Role> listRoles;
	


}
