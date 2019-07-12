package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.repository.MessageRepository;
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
    public void getByPhoneAndLessonId() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<MessageEntity> messageEntities = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setPhone(123);
        messageEntity.setLessonId(456);
        messageEntities.add(messageEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long a = (long)args[0];
            long b = (long)args[1];
            Assert.assertEquals(a,123);
            Assert.assertEquals(b,456);
            bitSet.set(0, true);
            return messageEntities;
        }).when(messageRepository).getByPhoneAndLessonId(Mockito.any(long.class),Mockito.any(long.class));


        mockMvc.perform(MockMvcRequestBuilders.get("/message/phoneAndLessonId")
                .param("phone","123")
                .param("lessonId","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getByPhone() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<MessageEntity> messageEntities = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setPhone(456);
        messageEntities.add(messageEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,456);
            bitSet.set(0, true);
            return messageEntities;
        }).when(messageRepository).getByPhone(Mockito.any(long.class));


        mockMvc.perform(MockMvcRequestBuilders.get("/message/phone")
                .param("phone","456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    public void insertMessage() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            MessageEntity messageEntity = (MessageEntity) args[0];
            Assert.assertEquals(messageEntity.getMessageId(),123);
            Assert.assertEquals(messageEntity.getMessage(),"计算机学院");
            bitSet.set(0, true);
            return null;
        }).when(messageRepository).save(Mockito.any(MessageEntity.class));

        String jsonData = "{\"messageId\":\"123\",\"message\":\"计算机学院\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/message")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void deleteMessage() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(messageRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/message")
                .param("id","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));
    }
}