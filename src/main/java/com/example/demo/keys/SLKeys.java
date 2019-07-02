package com.example.demo.keys;

import java.io.Serializable;

/**
 * @ Author     ：Theory
 * @ Description：学生选课表复合主键类
 */


public class SLKeys implements Serializable {

    private long phone;//学生手机号
    private long lessonId;//课程号

    public SLKeys() {
    }

    public SLKeys(long phone, long lessonId) {
        this.phone = phone;
        this.lessonId = lessonId;
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

    @Override
    public boolean equals(Object obj){
        SLKeys objKeys = (SLKeys)obj;
        if(phone ==objKeys.phone && lessonId == objKeys.lessonId)
            return true;
        else
            return false;
    }
}
