package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SLEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.SLRepository;
import com.example.demo.repository.StudentRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import java.util.BitSet;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SLControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    SLRepository slRepository;
    @MockBean
    LessonRepository lessonRepository;
    @MockBean
    StudentRepository studentRepository;

    @Test
    public void getAllSL() throws Exception{
        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setLessonId(123);
        slEntity.setPhone(456);
        slEntities.add(slEntity);
        Mockito.when(slRepository.findAll()).thenReturn(slEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/sl")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));
    }

    @Test
    public void getSLByStuId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntities.add(slEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return slEntities;
        }).when(slRepository).getSLByStuId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/stuId")
                .param("stuId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getSLByLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntities.add(slEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return slEntities;
        }).when(slRepository).getSLByStuId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/lessonId")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getLessonByStuId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);
        bitSet.set(1, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntities.add(slEntity);

        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(456);
        lessonEntity.setLessonName("篮球");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return slEntities;
        }).when(slRepository).getSLByStuId(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(1, true);
            return lessonEntity;
        }).when(lessonRepository).getByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getLessonByStuId")
                .param("stuId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("篮球")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
    }

    @Test
    public void getLessonPagesByStuId() {

    }

    @Test
    public void getLessonPagesNumByStuId() {
    }

    @Test
    public void getStudentByLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);
        bitSet.set(1, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntities.add(slEntity);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("别摸我");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return slEntities;
        }).when(slRepository).getSLByLessonId(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(1, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getStudentByLessonId")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("别摸我")));

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
    }

    @Test
    public void getEvaluationByLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntity.setEvaluation("啊哈哈");
        slEntities.add(slEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return slEntities;
        }).when(slRepository).getEvaluationByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getEvaluationByLessonId")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("啊哈哈")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getStuNumByLessonId() {

    }

    @Test
    public void insertSL() {
    }

    @Test
    public void updateSL() {
    }

    @Test
    public void deleteSL() {
    }
}