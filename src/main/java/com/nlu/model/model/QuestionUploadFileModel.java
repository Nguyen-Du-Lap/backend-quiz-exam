package com.nlu.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUploadFileModel {
    private String content;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correct;
}