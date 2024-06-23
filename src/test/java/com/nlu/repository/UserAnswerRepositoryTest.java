package com.nlu.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAnswerRepositoryTest {
    @Autowired UserAnswerRepository userAnswerRepository;
    @Test
    public void testGetListOption() {
        System.out.println(userAnswerRepository.getIdOptionByIdExamNumberAndIdUser(1, 5L));
    }
}
