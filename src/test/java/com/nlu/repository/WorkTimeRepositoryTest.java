package com.nlu.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkTimeRepositoryTest {
    @Autowired
    private WorkTimeRepository workTimeRepository;
    @Test
    public void testGetAllWorkTimes() {
        System.out.println(workTimeRepository.findAllByUser_IdOrderByBeginExamDesc(2L));
    }

}
