package com.nlu.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionResultResponse {
    Long id;
    String nameQuestion;
    Boolean correct;
    Long selectedOptionId;
}
