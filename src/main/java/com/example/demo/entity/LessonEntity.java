package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：Theory
 * @ Description：课程实体类
 */

@Entity
@Table(name = "Lesson")
public class LessonEntity {

    public LessonEntity() {
    }

    @Id
    @GeneratedValue
    private long lessonId;//课程号

    private String lessonName;//课程名

    private long schoolId;//学校序号

    private String education;//所属学历

    private String subject;//所属学科

    private int credit;//学分

    private String startTime;//开始时间

    private String endTime;//结束时间

    private String status;//状态

    private int studentNum;//学生数量

    private String lessonDir;//课程目录

    private String lessonIntro;//课程简介

    private String lessonOverview;//课程概述

    private String category;//课程类别

    private int welcome;//火热程度

    private boolean isExcellent = false;//是否精品

    private int shareNum;//分享次数


    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public String getLessonDir() {
        return lessonDir;
    }

    public void setLessonDir(String lessonDir) {
        this.lessonDir = lessonDir;
    }

    public String getLessonIntro() {
        return lessonIntro;
    }

    public void setLessonIntro(String lessonIntro) {
        this.lessonIntro = lessonIntro;
    }

    public String getLessonOverview() {
        return lessonOverview;
    }

    public void setLessonOverview(String lessonOverview) {
        this.lessonOverview = lessonOverview;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWelcome() {
        return welcome;
    }

    public void setWelcome(int welcome) {
        this.welcome = welcome;
    }


    public boolean isExcellent() {
        return isExcellent;
    }

    public void setExcellent(boolean excellent) {
        isExcellent = excellent;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }
}
