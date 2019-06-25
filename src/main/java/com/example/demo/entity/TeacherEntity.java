package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：Theory
 * @ Description：老师实体类
 */

@Entity
@Table(name = "Teacher")
public class TeacherEntity {

    @Id
    private long teacherId;

    private String teacherName;

    private String job;

    private long schoolId;
}
