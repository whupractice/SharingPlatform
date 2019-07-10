package com.example.demo.controller;

import com.example.demo.entity.SchoolEntity;
import com.example.demo.repository.SchoolRepository;
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
public class SchoolControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    SchoolRepository schoolRepository;

    @Test
    public void getAllSchool() throws Exception{
        List<SchoolEntity> schoolEntities = new ArrayList<>();
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolId(123);
        schoolEntity.setSchoolName("武汉大学");
        schoolEntities.add(schoolEntity);
        Mockito.when(schoolRepository.findAll()).thenReturn(schoolEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/school")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")));
    }

    @Test
    public void getSchoolById() {
    }

    @Test
    public void getSchoolByKeyword() {
    }

    @Test
    public void getSchoolByName() {
    }

    @Test
    public void insertSchool() {
    }

    @Test
    public void updateSchool() {
    }

    @Test
    public void deleteSchool() {
    }

    @Test
    public void uploadImg() {
    }
}