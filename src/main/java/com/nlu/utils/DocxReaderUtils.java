package com.nlu.utils;

import com.nlu.model.model.QuestionUploadFileModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DocxReaderUtils {

    public static List<QuestionUploadFileModel> readDocxFile(InputStream inputStream) {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            StringBuilder text = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }
            return extractQuestions(text.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<QuestionUploadFileModel> extractQuestions(String text) {
        List<QuestionUploadFileModel> questions = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        StringBuilder currentQuestion = new StringBuilder();
        String choiceA = "", choiceB = "", choiceC = "", choiceD = "", correct = "";
        boolean isQuestion = false;
        int count = -1;
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Câu ")) {
                if (isQuestion) {
                    questions.add(new QuestionUploadFileModel(currentQuestion.toString().trim(), choiceA, choiceB, choiceC, choiceD, correct));
                    currentQuestion.setLength(0);
                    choiceA = choiceB = choiceC = choiceD = correct = "";
                }
                // Remove the prefix "Câu X. " from the question content
                String questionContent = line.replaceFirst("Câu \\d+\\.\\s*", "").trim();
                currentQuestion.append(questionContent).append("\n");
                isQuestion = true;
                count = -1;
            } else {
                if(!line.isEmpty()) {
                    count++;
                    if (count == 0) choiceA = line.trim();
                    else if (count == 1) choiceB = line.trim();
                    else if (count == 2) choiceC = line.trim();
                    else choiceD = line.trim();
                }
            }
        }

        // Add the last question if there is one
        if (isQuestion) {
            questions.add(new QuestionUploadFileModel(currentQuestion.toString().trim(), choiceA, choiceB, choiceC, choiceD, ""));
        }

        return questions;
    }
}
