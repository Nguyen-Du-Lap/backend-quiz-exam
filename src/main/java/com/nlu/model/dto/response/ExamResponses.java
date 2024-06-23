package com.nlu.model.dto.response;

import com.nlu.model.entity.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResponses {
    private Long totalItems;
    private int totalPages;
    private int currentPage;
    private List<Exam> exams;
}
