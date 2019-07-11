package com.example.demo.controller;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.repository.TeacherRepository;
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
    public void insertTeacher() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TeacherEntity teacherEntity = (TeacherEntity) args[0];
            Assert.assertEquals(teacherEntity.getTeacherId(),123);
            Assert.assertEquals(teacherEntity.getTeacherName(),"计算机学院");
            bitSet.set(0, true);
            return null;
        }).when(teacherRepository).save(Mockito.any(TeacherEntity.class));

        String jsonData = "{\"teacherId\":\"123\",\"teacherName\":\"计算机学院\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void updateTeacher() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TeacherEntity teacherEntity = (TeacherEntity) args[0];
            Assert.assertEquals(teacherEntity.getTeacherId(),123);
            Assert.assertEquals(teacherEntity.getTeacherName(),"计算机学院");
            bitSet.set(0, true);
            return null;
        }).when(teacherRepository).save(Mockito.any(TeacherEntity.class));

        String jsonData = "{\"teacherId\":\"123\",\"teacherName\":\"计算机学院\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/teacher")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void deleteTeacher() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(teacherRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher")
                .param("teacherId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    public void getTeacherByKeyword() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TeacherEntity> teacherEntities = new ArrayList<>();
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(123);
        teacherEntity.setTeacherName("刘翔");
        teacherEntities.add(teacherEntity);



        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String id = (String)args[0];
            Assert.assertEquals(id,"%刘%");
            bitSet.set(0, true);
            return teacherEntities;
        }).when(teacherRepository).getTeacherByKeyword(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/keyword")
                .param("keyword","刘"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("刘翔")));

        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    public void getTeacherById() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(123);
        teacherEntity.setTeacherName("刘翔");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return teacherEntity;
        }).when(teacherRepository).getTeacherById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/id")
                .param("id","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("刘翔")));

        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void getTeacherBySchool() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TeacherEntity> teacherEntities = new ArrayList<>();
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(123);
        teacherEntity.setTeacherName("刘翔");
        teacherEntity.setSchoolName("加里敦大学");
        teacherEntities.add(teacherEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals(args[0],"加里敦大学");

            bitSet.set(0, true);
            return teacherEntities;
        }).when(teacherRepository).getTeacherBySchool(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/schoolName")
                .param("schoolName","加里敦大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));


        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    public void uploadImg() {

    }
}