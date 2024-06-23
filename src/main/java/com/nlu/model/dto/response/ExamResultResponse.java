package com.nlu.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamResultResponse {
    private String examName;
    private String studentCode;
    private String fullName;
    private String email;
    private String examNumberName;
    private Integer totalCorrect;
    private Integer totalQuestion;
    private Integer totalWrong;
    private Long totalTime;
    private Integer totalSkipped;
}
