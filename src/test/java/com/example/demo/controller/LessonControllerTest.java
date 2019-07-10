package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.repository.LessonRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.BitSet;
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
    @WithMockUser(roles={"manager"})
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
    public void getByLessonId() throws Exception{
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return lessonEntity;
        }).when(lessonRepository).getByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/id")
                .param("lessonId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));
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