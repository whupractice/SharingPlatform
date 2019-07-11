package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.TLEntity;
import com.example.demo.entity.TeacherEntity;
import com.example.demo.keys.TLKeys;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.TLRepository;
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
public class TLControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TLRepository tlRepository;
    @MockBean
    LessonRepository lessonRepository;
    @MockBean
    TeacherRepository teacherRepository;
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
    public void getTLByLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setLessonId(123);
        tlEntities.add(tlEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return tlEntities;
        }).when(tlRepository).getAllByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/tl/lessonId")
                .param("lessonId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")));

        Assert.assertTrue(bitSet.get(0));
    }


    @Test
    public void getTLByTeacherId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setTeacherId(123);
        tlEntities.add(tlEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return tlEntities;
        }).when(tlRepository).getAllByTeacherId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/tl/teacherId")
                .param("teacherId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")));

        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    public void getTeacherByLessonId() throws Exception { BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setTeacherId(123);
        tlEntity.setLessonId(456);
        tlEntities.add(tlEntity);

        TeacherEntity teacherEntity=new TeacherEntity();
        teacherEntity.setTeacherId(123);


        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return tlEntities;
        }).when(tlRepository).getAllByLessonId(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(1, true);
            return teacherEntity;
        }).when(teacherRepository).getTeacherById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/tl/getTeacherByLessonId")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")));
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getLessonByTeacherId() throws Exception {   BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);
        bitSet.set(1, false);

        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setTeacherId(123);
        tlEntity.setLessonId(456);
        tlEntities.add(tlEntity);

        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(456);
        lessonEntity.setLessonName("篮球");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return tlEntities;
        }).when(tlRepository).getAllByTeacherId(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(1, true);
            return lessonEntity;
        }).when(lessonRepository).getByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/tl/getLessonByTeacherId")
                .param("teacherId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("篮球")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void insertTL() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TLEntity tlEntity = (TLEntity) args[0];
            Assert.assertEquals(tlEntity.getTeacherId(),123);
            Assert.assertEquals(tlEntity.getLessonId(),456);

            bitSet.set(0, true);
            return null;
        }).when(tlRepository).save(Mockito.any(TLEntity.class));

        String jsonData = "{\"teacherId\":\"123\",\"lessonId\":\"456\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/tl")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void updateTL() throws Exception {

        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TLEntity tlEntity = (TLEntity) args[0];
            Assert.assertEquals(tlEntity.getTeacherId(),123);
            Assert.assertEquals(tlEntity.getLessonId(),456);

            bitSet.set(0, true);
            return null;
        }).when(tlRepository).save(Mockito.any(TLEntity.class));

        String jsonData = "{\"teacherId\":\"123\",\"lessonId\":\"456\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/tl")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test

    public void deleteTL() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TLKeys tlKeys = (TLKeys)args[0];

            Assert.assertEquals(tlKeys.getTeacherId(),456);
            Assert.assertEquals(tlKeys.getLessonId(),123);
            bitSet.set(0, true);
            return null;
        }).when(tlRepository).deleteById(Mockito.any(TLKeys.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/tl/lessonId/teacherId")
                .param("lessonId","123")
                .param("teacherId","456"))

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void deleteTLByTeacherId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<TLEntity> tlEntities = new ArrayList<>();
        TLEntity tlEntity = new TLEntity();
        tlEntity.setTeacherId(123);
        tlEntities.add(tlEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return tlEntities;
        }).when(tlRepository).getAllByTeacherId(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            TLEntity tlEntity1 = (TLEntity) args[0];
            Assert.assertEquals(tlEntity1.getTeacherId(),123);
            bitSet.set(0, true);
            return null;
        }).when(tlRepository).delete(Mockito.any(TLEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/tl/teacherId")
                .param("teacherId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }
}