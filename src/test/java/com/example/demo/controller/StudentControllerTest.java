package com.example.demo.controller;

import com.example.demo.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StudentRepository studentRepository;

    @Test
    public void getUserFromToken() {
    }

    @Test
    public void login() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void getNickNameByPhone() {
    }

    @Test
    public void register() {
    }

    @Test
    public void registerLessonManager() {
    }

    @Test
    public void registerManager() {
    }

    @Test
    public void getStuById() {
    }

    @Test
    public void getStuByNickName() {
    }

    @Test
    public void getStuBySchoolName() {
    }

    @Test
    public void getAllStudent() {
    }

    @Test
    public void getAllManager() {
    }

    @Test
    public void updateStudent() {
    }

    @Test
    public void updateLessonManager() {
    }

    @Test
    public void updateManager() {
    }

    @Test
    public void deleteLesson() {
    }

    @Test
    public void getLessonManagerPages() {
    }

    @Test
    public void getLessonManagerPagesByName() {
    }

    @Test
    public void getLessonManagerPagesBySchool() {
    }

    @Test
    public void getSkillImg() {
    }

    @Test
    public void getAllGraph() {
    }
}