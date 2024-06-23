package com.nlu.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="options")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name ="name_option")
	private String nameOption;
	
	private Boolean correct;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="question_id")
	private Question question;
	@JsonBackReference
	@OneToMany(mappedBy = "option",cascade = CascadeType.ALL)
	private Set<UserAnswer> listUserAnswers;
}
