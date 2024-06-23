package com.nlu.model.dto.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamStudentResponse {
	private Long studentId;
	private Integer examNumberId;
    private String title;
    private String fullname;
    private String code;
    private Integer timeExam;
    private Timestamp dateExam;
}
