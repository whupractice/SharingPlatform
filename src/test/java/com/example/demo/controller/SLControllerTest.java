package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SLEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.keys.SLKeys;
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
    @WithMockUser(roles={"student"})
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
    @WithMockUser(roles={"student"})
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
    @WithMockUser(roles={"student"})
    public void getLessonPagesByStuId() {

    }

    @Test
    @WithMockUser(roles={"student"})
    public void getLessonPagesNumByStuId() {
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
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
    public void getStuNumByLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return 8;
        }).when(slRepository).getStuNumByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getStuNumByLessonId")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("8")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("num")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void insertSL() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            SLEntity slEntity = (SLEntity) args[0];
            Assert.assertEquals(slEntity.getPhone(),123);
            Assert.assertEquals(slEntity.getLessonId(),456);
            bitSet.set(0, true);
            return null;
        }).when(slRepository).save(Mockito.any(SLEntity.class));

        String jsonData = "{\"phone\":\"123\",\"lessonId\":\"456\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/sl")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void updateSL() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            SLEntity slEntity = (SLEntity) args[0];
            Assert.assertEquals(slEntity.getPhone(),123);
            Assert.assertEquals(slEntity.getLessonId(),456);
            bitSet.set(0, true);
            return null;
        }).when(slRepository).save(Mockito.any(SLEntity.class));

        String jsonData = "{\"phone\":\"123\",\"lessonId\":\"456\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/sl")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void deleteSL() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            SLKeys slKeys = (SLKeys)args[0];
            Assert.assertEquals(slKeys.getPhone(),123);
            Assert.assertEquals(slKeys.getLessonId(),456);
            bitSet.set(0, true);
            return null;
        }).when(slRepository).deleteById(Mockito.any(SLKeys.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/sl/stuId/lessonId")
                .param("stuId","123")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }
}