package com.example.demo.controller;

import com.example.demo.entity.SchoolEntity;
import com.example.demo.repository.SchoolRepository;
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
    public void getSchoolById() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolId(123);
        schoolEntity.setSchoolName("加里敦大学");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return schoolEntity;
        }).when(schoolRepository).findById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/school/getSchoolById")
                .param("id","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getSchoolByKeyword() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<SchoolEntity> schoolEntities = new ArrayList<>();
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolId(123);
        schoolEntity.setSchoolName("加里敦大学");
        schoolEntities.add(schoolEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String id = (String)args[0];
            Assert.assertEquals(id,"加里敦");
            bitSet.set(0, true);
            return schoolEntities;
        }).when(schoolRepository).getSchoolByKeyword(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/school/keyword")
                .param("keyword","加里敦"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getSchoolByName() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolId(123);
        schoolEntity.setSchoolName("加里敦大学");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String id = (String)args[0];
            Assert.assertEquals(id,"加里敦大学");
            bitSet.set(0, true);
            return schoolEntity;
        }).when(schoolRepository).getSchoolByKeyword(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/school/schoolName")
                .param("schoolName","加里敦大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void insertSchool() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            SchoolEntity schoolEntity = (SchoolEntity) args[0];
            Assert.assertEquals(schoolEntity.getSchoolId(),123);
            Assert.assertEquals(schoolEntity.getSchoolName(),"加里敦大学");
            bitSet.set(0, true);
            return null;
        }).when(schoolRepository).save(Mockito.any(SchoolEntity.class));

        String jsonData = "{\"schoolId\":\"123\",\"schoolName\":\"加里敦大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/school")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void updateSchool() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            SchoolEntity schoolEntity = (SchoolEntity) args[0];
            Assert.assertEquals(schoolEntity.getSchoolId(),123);
            Assert.assertEquals(schoolEntity.getSchoolName(),"加里敦大学");
            bitSet.set(0, true);
            return null;
        }).when(schoolRepository).save(Mockito.any(SchoolEntity.class));

        String jsonData = "{\"schoolId\":\"123\",\"schoolName\":\"加里敦大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/school")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void deleteSchool() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(schoolRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/school")
                .param("schoolId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void uploadImg() {
    }
}