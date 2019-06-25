package com.example.demo.keys;

import org.springframework.data.jpa.repository.Query;

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

    @Override
    public boolean equals(Object obj){
        SLKeys objKeys = (SLKeys)obj;
        if(studentId==objKeys.studentId && lessonId == objKeys.lessonId)
            return true;
        else
            return false;
    }
}
