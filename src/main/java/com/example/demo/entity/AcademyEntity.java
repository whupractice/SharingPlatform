package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：QinYingran
 * @ Description：学院实体类
 */

@Entity
@Table(name = "academy")
public class AcademyEntity {
    AcademyEntity(){}

    @Id
    @GeneratedValue
    private long academyId;//学院号

    private String academyName;//学院名

    private String schoolName;//所属学校名

    public long getAcademyId() {
        return academyId;
    }

    public void setAcademyId(long academyId) {
        this.academyId = academyId;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
