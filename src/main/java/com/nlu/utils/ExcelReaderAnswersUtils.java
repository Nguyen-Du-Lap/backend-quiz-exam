package com.nlu.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReaderAnswersUtils {
    public static Map<Integer, String> read(InputStream inputStream) throws IOException {
        Map<Integer, String> answers = new HashMap<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Bỏ qua tiêu đề nếu có
                }
                Cell questionCell = row.getCell(0);
                Cell answerCell = row.getCell(1);

                if (questionCell != null && answerCell != null) {
                    int questionNumber = (int) questionCell.getNumericCellValue();
                    String answer = answerCell.getStringCellValue();
                    answers.put(questionNumber, answer);
                }
            }
        }
        return answers;
    }
}
