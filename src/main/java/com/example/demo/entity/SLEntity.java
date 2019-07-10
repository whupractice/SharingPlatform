package com.example.demo.entity;

import com.example.demo.keys.SLKeys;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @ Author     ：Theory
 * @ Description：学生选课表
 */

@Entity
@IdClass(SLKeys.class)
@Table(name="sl")
public class SLEntity {

    public SLEntity() {
    }


    @Id
    private long phone;//学生账号

    @Id
    private long lessonId;//课程号

    private String evaluation;//评价

    @ApiModelProperty(value = "星级",example = "1")
    private int star;//星级

    @ApiModelProperty(value = "赞数",example = "1")
    private int praiseNum;//赞数

    private String evaTime;//评价时间

    private String lessonProcess;//课程学习进度

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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getEvaTime() {
        return evaTime;
    }

    public void setEvaTime(String evaTime) {
        this.evaTime = evaTime;
    }

    public String getLessonProcess() {
        return lessonProcess;
    }

    public void setLessonProcess(String lessonProcess) {
        this.lessonProcess = lessonProcess;
    }
}
