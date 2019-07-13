package com.example.demo.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @ ClassName  ：NoMockTest
 * @ Author     ：QinYingran
 * @ Description：TODO
 * @ Date       ：2019-07-12 15:41
 * @ Version    ：1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoMockTest {
    @Autowired
    private MockMvc mockMvc;

    //LessonController
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
                .param("schoolName","加里敦大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));
    }

    @Test
    public void getLessonPagesByLessonName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/pagesByLessonName")
                .param("lessonName","篮球"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("篮球")));
    }

    @Test
    public void getLessonPagesBySchoolAndAcademy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/pagesBySchoolAndAcademy")
                .param("schoolName","加里敦大学")
                .param("academyName","自动化学院"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("自动化学院")));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getTjLessonByStuPhone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/tj")
                .param("phone","15629083301"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        //.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("体育课")));
    }


    //MessageController
    @Test
    public void getPagesByPhoneAndLessonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/message/getPagesByPhoneAndLessonId")
                .param("phone","15629083301")
                .param("lessonId","4"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("31")));
    }

    @Test
    public void getPagesByPhone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/message/getPagesByPhone")
                .param("phone","15629083301"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("31")));
    }


    //SLController
    @Test
    @WithMockUser(roles={"student"})
    public void getLessonPagesByStuId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getLessonPagesByStuId")
                .param("stuId","15629083301")
                .param("num","1")
                .param("page","0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getLessonPagesByStuId2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getLessonPagesByStuId")
                .param("stuId","6526634563")
                .param("num","1")
                .param("page","0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getLessonPagesByStuId3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getLessonPagesByStuId")
                .param("stuId","15629083301")
                .param("num","3")
                .param("page","1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getLessonPagesNumByStuId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sl/getLessonPagesNumByStuId")
                .param("stuId","15629083301")
                .param("num","1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("4")));
    }

    @Test
    public void getScore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sl/score")
                .param("lessonId","4"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("score")));
    }



    //StudentController
    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/lessonManagerPages"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("15642512301")));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPagesByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/lessonManagerPagesByName")
                .param("realName","月咏几斗"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("15642512301")));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPagesBySchool() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/lessonManagerPagesBySchool")
                .param("schoolName","加里敦大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("15642512301")));
    }


    @Test
    @WithMockUser(roles={"student"})
    public void getSkillImg() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/skill")
                .param("phone","15629083302"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getAllGraph() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/allGraph"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
