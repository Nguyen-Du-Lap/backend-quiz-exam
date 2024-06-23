package com.nlu.utils;

import com.nlu.model.model.QuestionUploadFileModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfReaderUtils {

    public static List<QuestionUploadFileModel> readPdfFile(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
//            System.out.println(text);
            return extractQuestions(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<QuestionUploadFileModel> extractQuestions(String text) {
        List<QuestionUploadFileModel> questions = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        StringBuilder currentQuestion = new StringBuilder();
        String choiceA = "", choiceB = "", choiceC = "", choiceD = "";
        boolean isQuestion = false;

        for (String line : lines) {
            if (line.startsWith("Câu ")) {
                if (isQuestion) {
                    questions.add(new QuestionUploadFileModel(currentQuestion.toString().trim(), choiceA, choiceB, choiceC, choiceD, ""));
                    currentQuestion.setLength(0);
                    choiceA = choiceB = choiceC = choiceD = "";
                }
                // Remove the prefix "Câu X. " from the question content
                String questionContent = line.replaceFirst("Câu \\d+\\.\\s*", "").trim();
                currentQuestion.append(questionContent).append("\n");
                isQuestion = true;
            } else if (line.startsWith("A.")) {
                choiceA = line.substring(2).trim();
            } else if (line.startsWith("B.")) {
                choiceB = line.substring(2).trim();
            } else if (line.startsWith("C.")) {
                choiceC = line.substring(2).trim();
            } else if (line.startsWith("D.")) {
                choiceD = line.substring(2).trim();
            } else {
                // Handle any additional lines in the question body
                if (isQuestion) {
                    currentQuestion.append(line).append("\n");
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
