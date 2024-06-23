package com.nlu.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkTimeResponse {
    private Long id;
    private Integer idExamNumber;
    private String title;
    private String time;
    private Integer completionTime;
    private Date workDay;
    private String result;
}
