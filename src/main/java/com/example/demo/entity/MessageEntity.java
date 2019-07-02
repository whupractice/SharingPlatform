package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：QinYingran
 * @ Description：学生课程消息实体类
 */
@Entity
@Table(name = "message")
public class MessageEntity {
    MessageEntity(){}
    @Id
    @GeneratedValue
    private long messageId;//消息号

    private String time;//消息发送时间

    private String message;//消息内容

    private long phone;//学生手机号（外键）

    private long lessonId;//课程号（外键）

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }
}
