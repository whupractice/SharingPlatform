package com.example.demo.entity;

import javax.persistence.*;

/**
 * @ Author     ：Theory
 * @ Description：老师实体类
 */

@Entity
@Table(name = "teacher")
public class TeacherEntity {

    public TeacherEntity() {
    }

    @Id
    @GeneratedValue
    private long teacherId;//教师号

    private String teacherName;//教师名

    private String job;//教师职称（教授、副教授）

    private String schoolName;//学校名

    private String academyName;//院系名称

    @Column(length = 1000)
    private String teacherIntro;//老师简介

    String imgLink;//老师图片链接


    public TeacherEntity(String teacherName, String job, String schoolName, String academyName, String teacherIntro, String imgLink) {
        this.teacherName = teacherName;
        this.job = job;
        this.schoolName = schoolName;
        this.academyName = academyName;
        this.teacherIntro = teacherIntro;
        this.imgLink = imgLink;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }



    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getTeacherIntro() {
        return teacherIntro;
    }

    public void setTeacherIntro(String teacherIntro) {
        this.teacherIntro = teacherIntro;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
