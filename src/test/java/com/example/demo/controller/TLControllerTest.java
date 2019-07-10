package com.example.demo.controller;

import com.example.demo.entity.TLEntity;
import com.example.demo.repository.TLRepository;
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
public class TLControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TLRepository tlRepository;

    @Test
    public void getAllTL() throws Exception {
        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setLessonId(123);
        tlEntity.setTeacherId(456);
        tlEntities.add(tlEntity);
        Mockito.when(tlRepository.findAll()).thenReturn(tlEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/tl")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));
    }

    @Test
    public void getTLByLessonId() {
    }

    @Test
    public void getTLByTeacherId() {
    }

    @Test
    public void getTeacherByLessonId() {
    }

    @Test
    public void getLessonByTeacherId() {
    }

    @Test
    public void insertTL() {
    }

    @Test
    public void updateTL() {
    }

    @Test
    public void deleteTL() {
    }

    @Test
    public void deleteTLByTeacherId() {
    }
}