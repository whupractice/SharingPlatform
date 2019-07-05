package com.example.demo.service;

import com.example.demo.entity.MessageEntity;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：QinYingran
 * @ Description：学生课程消息逻辑服务类
 */
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public List<MessageEntity> getAll() {
        return messageRepository.findAll();
    }

    public Page<MessageEntity> getAll(Specification<MessageEntity> specification, Pageable pageable) {
        return messageRepository.findAll(specification,pageable);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学生手机号和课程号获取消息列表
      * @Param       : [phone, lessonId]
      * @return      : java.util.List<com.example.demo.entity.MessageEntity>
      */
    public List<MessageEntity> getByPhoneAndLessonId(String phone, String lessonId) {
        long newPhone = Long.parseLong(phone);
        long newLessonId = Long.parseLong(lessonId);
        return messageRepository.getByPhoneAndLessonId(newPhone,newLessonId);
    }

    public List<MessageEntity> getByPhone(String phone) {
        long newPhone = Long.parseLong(phone);
        return messageRepository.getByPhone(newPhone);
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库中插入学生课程消息
      * @Param       : [messageEntity]
      * @return      : void
      */
    public void insertMessage(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据id删除学生课程消息
      * @Param       : [id]
      * @return      : void
      */
    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }


}
