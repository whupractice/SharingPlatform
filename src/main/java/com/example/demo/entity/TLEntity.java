package com.example.demo.entity;


import com.example.demo.keys.TLKeys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @ Author     ：Theory
 * @ Description：课程教师安排表
 */

@Entity
@IdClass(TLKeys.class)
@Table(name="TL")
public class TLEntity {

    @Id
    private long lessonId;//课程号

    @Id
    private long teacherId;//教师号

    public TLEntity() {
    }

    public TLEntity(long lessonId, long teacherId) {
        this.lessonId = lessonId;
        this.teacherId = teacherId;
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
