package com.example.demo.keys;

import java.io.Serializable;

/**
 * @ Author     ：Theory
 * @ Description：课程教师复合主键类
 */
public class TLKeys implements Serializable {

    private long teacherId;//老师id
    private long lessonId;//课程号

    public TLKeys() {
    }

    public TLKeys(long teacherId, long lessonId) {
        this.teacherId = teacherId;
        this.lessonId = lessonId;
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

    @Override
    public boolean equals(Object obj){
        TLKeys objKeys = (TLKeys)obj;
        if(teacherId == objKeys.teacherId && lessonId == objKeys.lessonId)
            return true;
        else
            return false;
    }
}
