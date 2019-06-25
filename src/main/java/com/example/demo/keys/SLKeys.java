package com.example.demo.keys;

import java.io.Serializable;

/**
 * @ Author     ：Theory
 * @ Description：学生选课表复合主键类
 */


public class SLKeys implements Serializable {

    private long studentId;//学生账号
    private long lessonId;//课程号

    public SLKeys() {
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }
}
