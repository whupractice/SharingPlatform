package com.example.demo.keys;

import java.io.Serializable;

/**
 * @ Author     ：Theory
 * @ Description：课程教师复合主键类
 */
public class TLKeys implements Serializable {


    private long lessonId;//课程号

    private long teacherId;//老师id

    public TLKeys() {
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
