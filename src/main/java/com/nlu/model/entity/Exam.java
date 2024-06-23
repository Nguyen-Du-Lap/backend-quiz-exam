package com.nlu.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name = "Exams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	@Column(name = "code_group")
	private String codeGroup = UUID.randomUUID().toString();
	@Column(name = "short_description")
	private String shortDescription;
	private String description;
	@Column(name = "quantity_question")
	private int quantityQuestion;
	private Timestamp startTime;
	private Timestamp endTime;

	@Column(name = "is_public")
	private boolean isPublic;

	@OneToMany(mappedBy = "exam", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ExamNumber> examNumbers;

	@ManyToOne(optional = false)
	@JoinColumn(name = "time_id")
	private TimeExam timeExam;

	@ManyToOne(optional = false)
	@JoinColumn(name = "teacher_id")
	private User teacher;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	public Exam(Long id, String title, String description, int quantityQuestion, TimeExam timeExam) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.quantityQuestion = quantityQuestion;
		this.timeExam = timeExam;
	}
}
