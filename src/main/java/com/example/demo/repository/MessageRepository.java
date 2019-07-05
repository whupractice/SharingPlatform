package com.example.demo.repository;

import com.example.demo.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : QinYingran
  * @Description : 学生课程信息
  * @Param       :
  * @return      :
  */
public interface MessageRepository extends JpaRepository<MessageEntity,Long>, JpaSpecificationExecutor<MessageEntity> {

    //根据学生手机号和课程号获取消息列表
    @Query(value = "select * from message where phone = ?1 and lesson_id = ?2",nativeQuery = true)
    List<MessageEntity> getByPhoneAndLessonId(long phone, long lessonId);

    //根据学生手机号获取消息列表
    @Query(value = "select * from message where phone = ?1",nativeQuery = true)
    List<MessageEntity> getByPhone(long phone);
}
