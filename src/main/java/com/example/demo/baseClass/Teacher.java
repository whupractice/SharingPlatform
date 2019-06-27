package com.example.demo.baseClass;

/**
 * @ Author     ：Theory
 * @ Description：爬取的教师类
 */
public class Teacher {

    String teacherName;
    String job;
    String schoolName;
    String academyName;
    String introduction;
    String imgLink;

    public Teacher() {
    }

    public Teacher(String teacherName, String job, String schoolName, String academyName, String introduction, String imgLink) {
        this.teacherName = teacherName;
        this.job = job;
        this.schoolName = schoolName;
        this.academyName = academyName;
        this.introduction = introduction;
        this.imgLink = imgLink;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "teacherName='" + teacherName + '\'' +
                ", job='" + job + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", academyName='" + academyName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }
}
