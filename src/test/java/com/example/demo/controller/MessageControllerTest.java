package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.repository.MessageRepository;
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
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    MessageRepository messageRepository;

    @Test
    public void getAll() throws Exception {
        List<MessageEntity> messageEntities = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setLessonId(123);
        messageEntity.setPhone(456);
        messageEntities.add(messageEntity);
        Mockito.when(messageRepository.findAll()).thenReturn(messageEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/message")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));
    }

    @Test
    public void getByPhoneAndLessonId() {
    }

    @Test
    public void getByPhone() {
    }

    @Test
    public void getPagesByPhoneAndLessonId() {
    }

    @Test
    public void getPagesByPhone() {
    }

    @Test
    public void insertMessage() {
    }

    @Test
    public void deleteMessage() {
    }
}