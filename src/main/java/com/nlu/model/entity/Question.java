package com.nlu.model.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nlu.model.dto.response.QuestionResultResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@SqlResultSetMapping(
		name = "QuestionResultMapping",
		classes = @ConstructorResult(
				targetClass = QuestionResultResponse.class,
				columns = {
						@ColumnResult(name = "id", type = Long.class),
						@ColumnResult(name = "name_question", type = String.class),
						@ColumnResult(name = "correct", type = Boolean.class),
						@ColumnResult(name = "selected_option_id", type = Long.class)
				}
		)
)
@NamedNativeQuery(
		name = "Question.questionResult",
		query = "SELECT q.id, q.name_question, sub.correct, sub.selected_option_id " +
				"FROM questions q " +
				"JOIN examnumber_ques eq ON q.id = eq.question_id " +
				"LEFT JOIN (" +
				"    SELECT q.id, o.correct, ua.option_id AS selected_option_id " +
				"    FROM questions q " +
				"    JOIN examnumber_ques eq ON q.id = eq.question_id " +
				"    JOIN options o ON q.id = o.question_id " +
				"    LEFT JOIN user_answers ua ON o.id = ua.option_id " +
				"    WHERE ua.user_id = :userId AND eq.examnumber_id = :examNumberId " +
				") sub ON sub.id = q.id " +
				"WHERE eq.examnumber_id = :examNumberId",
		resultSetMapping = "QuestionResultMapping"
)
@Table(name = "questions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name_question")
	private String nameQuestion;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Option> options;

	@JsonIgnore
	@ManyToMany(mappedBy = "listQuestions", fetch = FetchType.EAGER)
	private Set<ExamNumber> listExamNumbers;

	public Question(Long id, String nameQuestion, Set<Option> options) {
		this.id = id;
		this.nameQuestion = nameQuestion;
		this.options = options;
	}
}
