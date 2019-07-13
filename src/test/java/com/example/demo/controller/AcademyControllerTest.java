package com.example.demo.controller;

import com.example.demo.entity.AcademyEntity;
import com.example.demo.repository.AcademyRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class AcademyControllerTest {

    @MockBean
    AcademyRepository academyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll() throws Exception{
        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setAcademyName("计算机学院");
        academyEntities.add(academyEntity);

        Mockito.when(academyRepository.findAll()).thenReturn(academyEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/academy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("计算机学院")));
    }

    @Test
    public void getByAcademyName() throws Exception {
        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setAcademyName("计算机学院");
        academyEntities.add(academyEntity);
        Mockito.when(academyRepository.getByAcademyName("计算机学院")).thenReturn(academyEntities);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/academy/academyName")
                .param("academyName","计算机学院"))

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("计算机学院")));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void getBySchoolName() throws Exception{
        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setSchoolName("武汉大学");
        academyEntities.add(academyEntity);
        Mockito.when(academyRepository.getBySchoolName("武汉大学")).thenReturn(academyEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/academy/schoolName")
                .param("schoolName","武汉大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")));
    }

    @Test
    public void getBySchoolNameAndAcademyName() throws Exception{
        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setAcademyName("计算机学院");
        academyEntity.setSchoolName("武汉大学");
        academyEntities.add(academyEntity);
        Mockito.when(academyRepository.getBySchoolNameAndAcademyName("武汉大学","计算机学院")).thenReturn(academyEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/academy/schoolNameAndAcademyName")
                .param("schoolName","武汉大学")
                .param("academyName","计算机学院"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("计算机学院")));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void insertAcademy() throws Exception {
        BitSet bitSet = new BitSet(2);
        bitSet.set(0, false);
        bitSet.set(1, false);

        List<AcademyEntity> academyEntities = new ArrayList<>();

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            AcademyEntity academyEntity1 = (AcademyEntity) args[0];
            Assert.assertEquals(academyEntity1.getAcademyId(),123);
            Assert.assertEquals(academyEntity1.getAcademyName(),"计算机学院");
            Assert.assertEquals(academyEntity1.getSchoolName(),"武汉大学");
            bitSet.set(0, true);
            return null;
        }).when(academyRepository).save(Mockito.any(AcademyEntity.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String academyName = (String) args[0];
            Assert.assertEquals(academyName,"计算机学院");
            bitSet.set(1, true);
            return academyEntities;
        }).when(academyRepository).getByAcademyName(Mockito.any(String.class));

        String jsonData = "{\"academyId\":\"123\",\"academyName\":\"计算机学院\",\"schoolName\":\"武汉大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/academy")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void insertAcademy2() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<AcademyEntity> academyEntities = new ArrayList<>();
        AcademyEntity academyEntity = new AcademyEntity();
        academyEntity.setAcademyId(123);
        academyEntity.setAcademyName("计算机学院");
        academyEntity.setSchoolName("武汉大学");
        academyEntities.add(academyEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String academyName = (String) args[0];
            Assert.assertEquals(academyName,"计算机学院");
            bitSet.set(0, true);
            return academyEntities;
        }).when(academyRepository).getByAcademyName(Mockito.any(String.class));

        String jsonData = "{\"academyId\":\"123\",\"academyName\":\"计算机学院\",\"schoolName\":\"武汉大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/academy")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void deleteByAcademyId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(academyRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/academy")
                .param("id","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }
}