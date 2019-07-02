package com.example.demo.entity;

import javax.persistence.*;

/**
 * @ Author     ：Theory
 * @ Description：课程实体类
 */

@Entity
@Table(name = "Lesson")
public class LessonEntity {

    public LessonEntity() {
    }

    public LessonEntity(String lessonName, String schoolName, String education, String subject, int credit, String startTime, String endTime, String status,  String lessonIntro, int welcome, int recommendedLevel, int shareNum, String imgLink) {
        this.lessonName = lessonName;
        this.schoolName = schoolName;
        this.education = education;
        this.subject = subject;
        this.credit = credit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.lessonIntro = lessonIntro;
        this.welcome = welcome;
        this.recommendedLevel = recommendedLevel;
        this.shareNum = shareNum;
        this.imgLink = imgLink;
    }

    @Id
    @GeneratedValue
    private long lessonId;//课程号

    private String lessonName;//课程名

    private String schoolName;//所属学校名

    private String subject;//所属学科

    private String education;//所属学历

    private String status;//状态

    private String startTime;//开始时间

    private String endTime;//结束时间

    private int credit;//学分

    @Column(length = 2000)
    private String lessonIntro;//课程简介

    private int welcome;//火热程度

    private int recommendedLevel;//推荐级别

    private int shareNum;//分享次数

    private String imgLink;//课程图片链接


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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getLessonIntro() {
        return lessonIntro;
    }

    public void setLessonIntro(String lessonIntro) {
        this.lessonIntro = lessonIntro;
    }

    public int getWelcome() {
        return welcome;
    }

    public void setWelcome(int welcome) {
        this.welcome = welcome;
    }

    public int getRecommendedLevel() {
        return recommendedLevel;
    }

    public void setRecommendedLevel(int recommendedLevel) {
        this.recommendedLevel = recommendedLevel;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
