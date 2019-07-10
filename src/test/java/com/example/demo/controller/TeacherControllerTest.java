package com.example.demo.controller;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.repository.TeacherRepository;
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
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TeacherRepository teacherRepository;

    @Test
    public void getAllTeacher() throws Exception {
        List<TeacherEntity> teacherEntities = new ArrayList<>();
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(123);
        teacherEntity.setTeacherName("刘翔");
        teacherEntities.add(teacherEntity);
        Mockito.when(teacherRepository.findAll()).thenReturn(teacherEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("刘翔")));
    }

    @Test
    public void insertTeacher() {
    }

    @Test
    public void updateTeacher() {
    }

    @Test
    public void deleteTeacher() {
    }

    @Test
    public void getTeacherByKeyword() {
    }

    @Test
    public void getTeacherById() {
    }

    @Test
    public void getTeacherBySchool() {
    }

    @Test
    public void uploadImg() {
    }
}