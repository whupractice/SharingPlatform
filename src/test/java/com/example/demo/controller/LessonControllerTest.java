package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.repository.LessonRepository;
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
public class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    LessonRepository lessonRepository;

    @Test
    public void getAllLesson() throws Exception{
        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");
        lessonEntities.add(lessonEntity);
        Mockito.when(lessonRepository.findAll()).thenReturn(lessonEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));
    }

    @Test
    public void getByLessonId() {
    }

    @Test
    public void getExcellentLesson() {
    }

    @Test
    public void getHotLesson() {
    }

    @Test
    public void getBySchoolName() {
    }

    @Test
    public void getLessonByKeyword() {
    }

    @Test
    public void getLessonPages() {
    }

    @Test
    public void getLessonPagesBySchoolName() {
    }

    @Test
    public void getLessonPagesByLessonName() {
    }

    @Test
    public void insertLesson() {
    }

    @Test
    public void updateLesson() {
    }

    @Test
    public void deleteLesson() {
    }

    @Test
    public void getLessonsBySchoolAndAcademy() {
    }

    @Test
    public void getLessonPagesBySchoolAndAcademy() {
    }

    @Test
    public void getTjLessonByStuPhone() {
    }

    @Test
    public void uploadImg() {
    }

    @Test
    public void uploadVideo() {
    }
}