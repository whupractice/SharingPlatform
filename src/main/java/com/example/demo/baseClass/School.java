package com.example.demo.baseClass;

/**
 * @ Author     ：Theory
 * @ Description：爬取的学校类
 */
public class School {

    String schoolDetailLink;//学校详情链接

    String schoolIntro;//学校简介

    String imgLink;//图片链接

    public School(String schoolDetailLink, String imgLink) {
        this.schoolDetailLink = schoolDetailLink;
        this.imgLink = imgLink;
    }

    public String getSchoolDetailLink() {
        return schoolDetailLink;
    }

    public void setSchoolDetailLink(String schoolDetailLink) {
        this.schoolDetailLink = schoolDetailLink;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getSchoolIntro() {
        return schoolIntro;
    }

    public void setSchoolIntro(String schoolIntro) {
        this.schoolIntro = schoolIntro;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolDetailLink='" + schoolDetailLink + '\'' +
                ", schoolIntro='" + schoolIntro + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }
}
