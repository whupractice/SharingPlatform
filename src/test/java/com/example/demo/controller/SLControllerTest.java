package com.example.demo.controller;

import com.example.demo.entity.SLEntity;
import com.example.demo.repository.SLRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SLControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    SLRepository slRepository;

    @Test
    public void getAllSL() throws Exception{
        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setLessonId(123);
        slEntity.setPhone(456);
        slEntities.add(slEntity);
        Mockito.when(slRepository.findAll()).thenReturn(slEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/sl")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));
    }

    @Test
    public void getSLByStuId() {
    }

    @Test
    public void getSLByLessonId() {
    }

    @Test
    public void getLessonByStuId() {
    }

    @Test
    public void getLessonPagesByStuId() {
    }

    @Test
    public void getLessonPagesNumByStuId() {
    }

    @Test
    public void getStudentByLessonId() {
    }

    @Test
    public void getEvaluationByLessonId() {
    }

    @Test
    public void getStuNumByLessonId() {
    }

    @Test
    public void insertSL() {
    }

    @Test
    public void updateSL() {
    }

    @Test
    public void deleteSL() {
    }
}