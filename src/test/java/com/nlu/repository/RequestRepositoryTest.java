package com.nlu.repository;

import com.nlu.model.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RequestRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Test
    public void testFindAllQuestionResult() {

    }
}
