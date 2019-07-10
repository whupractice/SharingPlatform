package com.example.demo.controller;

import com.example.demo.entity.AcademyEntity;
import com.example.demo.repository.AcademyRepository;
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
public class AcademyControllerTest {

    @MockBean
    AcademyRepository academyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AcademyController academyController;

    @Test
    public void getAll() throws Exception{
        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setAcademyName("计算机学院");
        academyEntities.add(academyEntity);
        Mockito.when(academyRepository.findAll()).thenReturn(academyEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/academy")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("计算机学院")));
    }

    @Test
    public void getByAcademyName() {
    }

    @Test
    public void getBySchoolName() {
    }

    @Test
    public void getBySchoolNameAndAcademyName() {
    }

    @Test
    public void insertAcademy() {
    }

    @Test
    public void deleteByAcademyId() {
    }
}