package com.example.demo.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：Theory
 * @ Description：学校实体类
 */

@Entity
@Table(name = "School")
public class SchoolEntity {


    @Id
    @GeneratedValue
    private long schoolId;//院校号

    private String schoolName;//院校名

    private String schoolItro;//院校简介


    public SchoolEntity() {

    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolItro() {
        return schoolItro;
    }

    public void setSchoolItro(String schoolItro) {
        this.schoolItro = schoolItro;
    }
}
