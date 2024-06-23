package com.nlu.model.dto.request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nlu.model.entity.Exam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamRequest implements Serializable {

    @NotBlank(message = "{exam_title_blank}")
    String title;

    String shortDescription;
    @NotBlank(message = "{exam_description_blank}")
    String description;

    @NotNull(message = "{exam_quantity_question_null}")
    @Min(value = 1, message = "{exam_quantity_question_min}")
    Integer quantityQuestion;

    @NotNull(message = "{exam_time_id_null}")
    Integer timeId;

    @NotNull(message = "{exam_category_id_null}")
    Integer categoryId;

    @NotNull(message = "{exam_start_time_null}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime startTime;

    @NotNull(message = "{exam_end_time_null}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime endTime;

    @NotNull(message = "{exam_is_public_null}")
    Boolean isPublic;

    @NotNull(message = "{exam_list_exam_number_requests_null}")
    @Size(min = 1, message = "{exam_list_exam_number_requests_size}")
    List<@Valid ExamNumberRequest> listExamNumberRequests;

    @NotNull(message = "{exam_list_question_requests_null}")
    @Size(min = 1, message = "{exam_list_question_requests_size}")
    List<@Valid QuestionRequest> listQuestionRequests;

    public static void setForEntity(Exam exam, ExamRequest request) {
        exam.setShortDescription(request.getShortDescription());
        exam.setDescription(request.getDescription());
        exam.setTitle(request.getTitle());
        exam.setPublic(request.getIsPublic());
        exam.setQuantityQuestion(request.getQuantityQuestion());
        exam.setStartTime(Timestamp.valueOf(request.getStartTime()));
        exam.setEndTime(Timestamp.valueOf(request.getEndTime()));
    }

}
