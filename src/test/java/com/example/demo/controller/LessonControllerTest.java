package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.repository.LessonRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
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
public class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    LessonRepository lessonRepository;

    @Test
    @WithMockUser(roles={"manager"})
    public void getAllLesson() throws Exception{
        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");
        lessonEntities.add(lessonEntity);
        Mockito.when(lessonRepository.findAll()).thenReturn(lessonEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));
    }

    @Test
    public void getByLessonId() throws Exception{
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return lessonEntity;
        }).when(lessonRepository).getByLessonId(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/id")
                .param("lessonId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getExcellentLesson() throws Exception {
        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");
        lessonEntities.add(lessonEntity);
        Mockito.when(lessonRepository.findAll()).thenReturn(lessonEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/excellent"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));
    }

    @Test
    public void getHotLesson() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setLessonName("体育课");
        lessonEntities.add(lessonEntity);

        Mockito.doAnswer(invocationOnMock -> {
            bitSet.set(0, true);
            return lessonEntities;
        }).when(lessonRepository).findAll(Mockito.any(Sort.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/hot")
                .param("lessonNum","2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getBySchoolName() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setSchoolName("武汉大学");
        lessonEntities.add(lessonEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String school = (String) args[0];
            Assert.assertEquals(school,"武汉大学");
            bitSet.set(0, true);
            return lessonEntities;
        }).when(lessonRepository).getBySchoolName(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/schoolName")
                .param("schoolName","武汉大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")));

        Assert.assertTrue(bitSet.get(0));
    }


    @Test
    public void getLessonPages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/pages")
                .param("status","正在开课")
                .param("subject","哲学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("正在开课")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("哲学")));
    }

    @Test
    public void getLessonPagesBySchoolName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/pagesBySchoolName")
                .param("schoolName","武汉大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")));
    }

    @Test
    public void getLessonPagesByLessonName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/pagesByLessonName")
                .param("lessonName","篮球课"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("篮球课")));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void insertLesson() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            LessonEntity lessonEntity = (LessonEntity) args[0];
            Assert.assertEquals(lessonEntity.getLessonId(),123);
            Assert.assertEquals(lessonEntity.getLessonName(),"篮球课");
            Assert.assertEquals(lessonEntity.getSchoolName(),"武汉大学");
            bitSet.set(0, true);
            return null;
        }).when(lessonRepository).save(Mockito.any(LessonEntity.class));

        String jsonData = "{\"lessonId\":\"123\",\"lessonName\":\"篮球课\",\"schoolName\":\"武汉大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/lesson")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void updateLesson() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            LessonEntity lessonEntity = (LessonEntity) args[0];
            Assert.assertEquals(lessonEntity.getLessonId(),123);
            Assert.assertEquals(lessonEntity.getLessonName(),"篮球课");
            Assert.assertEquals(lessonEntity.getSchoolName(),"武汉大学");
            bitSet.set(0, true);
            return null;
        }).when(lessonRepository).save(Mockito.any(LessonEntity.class));

        String jsonData = "{\"lessonId\":\"123\",\"lessonName\":\"篮球课\",\"schoolName\":\"武汉大学\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/lesson")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void deleteLesson() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(lessonRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/lesson")
                .param("lessonId","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void getLessonsBySchoolAndAcademy() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<LessonEntity> lessonEntities = new ArrayList<>();
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(123);
        lessonEntity.setSchoolName("武汉大学");
        lessonEntity.setAcademyName("计算机学院");
        lessonEntities.add(lessonEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals(args[0],"武汉大学");
            Assert.assertEquals(args[1],"计算机学院");
            bitSet.set(0, true);
            return lessonEntities;
        }).when(lessonRepository).getLessonsBySchoolAndAcademy(Mockito.any(String.class),Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/schoolAndAcademy")
                .param("schoolName","武汉大学")
                .param("academyName","计算机学院"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("武汉大学")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("计算机学院")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getLessonPagesBySchoolAndAcademy() {

    }

    @Test
    public void getTjLessonByStuPhone() {
    }

    @Test
    public void uploadImg() {
    }

    @Test
    public void uploadVideo() {
    }
}